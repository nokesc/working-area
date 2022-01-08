package org.nebula.io;

import java.io.Closeable;
import java.io.Flushable;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class IOTemplate {

	public static final IOTemplate IO_TEMPLATE = new IOTemplate();

	public static UncheckedIOException wrap(IOException ioe) {
		try {
			throw ioe;
		} catch (IOException e) {
			return new UncheckedIOException(e);
		}
	}

	public static final class CloseStrategies {
		private static final Logger LOGGER = LoggerFactory.getLogger(CloseStrategies.class);

		public static final Optional<UncheckedIOException> ERROR(IOException ioe) {
			LOGGER.error("Closeable.close() failed", ioe);
			return Optional.empty();
		}

		public static final Optional<UncheckedIOException> WARN(IOException ioe) {
			LOGGER.warn("Closeable.close() failed", ioe);
			return Optional.empty();
		}

		public static final Optional<UncheckedIOException> RUNTIME_EX(IOException ioe) {
			return Optional.of(wrap(ioe));
		}
	}

	@FunctionalInterface
	public interface OutSupplier<O extends Closeable & Flushable> {
		O get() throws IOException;
	}

	@FunctionalInterface
	public interface InSupplier<I extends Closeable> {
		I get() throws IOException;
	}

	@FunctionalInterface
	public interface CloseIOExceptionHandler extends Function<IOException, Optional<? extends RuntimeException>> {

	}

	@FunctionalInterface
	public interface IOExceptionConverter extends Function<IOException, RuntimeException> {

	}

	@FunctionalInterface
	public interface SupplierWithIO<R> {
		R get() throws IOException;
	}

	@FunctionalInterface
	public interface RunnableWithIO {
		void run() throws IOException;
	}

	@FunctionalInterface
	public interface FunctionWithIO<IO extends Closeable, R> {
		R apply(IO io) throws IOException;
	}

	@FunctionalInterface
	public interface ConsumerWithIO<IO extends Closeable> {
		void accept(IO t) throws IOException;
	}

	@AllArgsConstructor
	public static class CloseAdapter<IO extends Closeable> implements AutoCloseable {

		public static final <IO extends Closeable> CloseAdapter<IO> RUNTIME_EX(IO io) {
			return new CloseAdapter<>(io, CloseStrategies::RUNTIME_EX);
		}

		public static final <IO extends Closeable> CloseAdapter<IO> LOG_ERROR_ONLY(IO io) {
			return new CloseAdapter<>(io, CloseStrategies::ERROR);
		}

		public static final <IO extends Closeable> CloseAdapter<IO> LOG_WARN_ONLY(IO io) {
			return new CloseAdapter<>(io, CloseStrategies::WARN);
		}

		private final IO closeable;
		private final CloseIOExceptionHandler closeIOExceptionHandler;

		public IO get() {
			return closeable;
		}

		@Override
		public void close() {
			try {
				closeable.close();
			} catch (IOException e) {
				Optional<? extends RuntimeException> optRe = closeIOExceptionHandler.apply(e);
				if (optRe.isPresent()) {
					throw optRe.get();
				}
			}
		}
	}

	private final CloseIOExceptionHandler closeIOExceptionHandler;
	private final IOExceptionConverter ioExceptionConverter;
	private final boolean autoFlush;

	public IOTemplate() {
		this(CloseStrategies::RUNTIME_EX, IOTemplate::wrap, true);
	}

	public <R> R get(final SupplierWithIO<R> function) {
		try {
			return function.get();
		} catch (IOException ioe) {
			throw ioExceptionConverter.apply(ioe);
		}
	}

	public void run(final RunnableWithIO runnable) {
		try {
			runnable.run();
		} catch (IOException ioe) {
			throw ioExceptionConverter.apply(ioe);
		}
	}

	protected <I extends Closeable> I getIn(final InSupplier<I> inSupplier) {
		try {
			return inSupplier.get();
		} catch (IOException e) {
			throw ioExceptionConverter.apply(e);
		}
	}

	public <I extends Closeable, R> R read(final InSupplier<I> inSupplier, final FunctionWithIO<I, R> function) {
		try (CloseAdapter<I> adapter = new CloseAdapter<>(getIn(inSupplier), closeIOExceptionHandler)) {
			return get(() -> function.apply(adapter.get()));
		}
	}

	public <I extends Closeable> void readConsumer(final InSupplier<I> inSupplier, final ConsumerWithIO<I> consumer) {
		try (CloseAdapter<I> closeAdapter = new CloseAdapter<>(getIn(inSupplier), closeIOExceptionHandler)) {
			run(() -> consumer.accept(closeAdapter.get()));
		}
	}

	protected <O extends Closeable & Flushable> O getOut(final OutSupplier<O> outSupplier) {
		return get(() -> outSupplier.get());
	}

	public <O extends Closeable & Flushable, R> R write(final OutSupplier<O> outSupplier,
			final FunctionWithIO<O, R> function) {
		final O io = getOut(outSupplier);
		final CloseAdapter<O> adapter = new CloseAdapter<>(io, closeIOExceptionHandler);
		try (adapter) {
			return get(() -> {
				final R result = function.apply(io);
				if (autoFlush) {
					io.flush();
				}
				return result;
			});
		}
	}

	public <O extends Closeable & Flushable> void writeConsumer(final OutSupplier<O> outSupplier,
			final ConsumerWithIO<O> function) {
		try (CloseAdapter<O> closeAdapter = new CloseAdapter<>(getOut(outSupplier), closeIOExceptionHandler)) {
			run(() -> {
				function.accept(closeAdapter.get());
				if (autoFlush) {
					closeAdapter.get().flush();
				}
			});
		}
	}

	public IOTemplate $closeIOException_logWarnOnly() {
		return $closeIOExceptionHandler(CloseStrategies::WARN);
	}

	public IOTemplate $closeIOException_logErrorOnly() {
		return $closeIOExceptionHandler(CloseStrategies::ERROR);
	}

	public IOTemplate $closeIOExceptionHandler(CloseIOExceptionHandler closeIOExceptionHandler) {
		return new IOTemplate(closeIOExceptionHandler, this.ioExceptionConverter, this.autoFlush);
	}

	public IOTemplate $ioExceptionConverter(IOExceptionConverter ioExceptionConverter) {
		return new IOTemplate(this.closeIOExceptionHandler, ioExceptionConverter, this.autoFlush);
	}

	public IOTemplate $autoFlush(boolean autoFlush) {
		return new IOTemplate(this.closeIOExceptionHandler, this.ioExceptionConverter, autoFlush);
	}
}