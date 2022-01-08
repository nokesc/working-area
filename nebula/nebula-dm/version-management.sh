#!/bin/bash -e

rules="file://${PWD}/rules.xml"

case $1 in
	property-updates)
		mvn -N -Dmaven.version.rules="${rules}" versions:display-property-updates
	;;
	update-properties)
		mvn -N -Dmaven.version.rules="${rules}" versions:update-properties
	;;
	parent-updates)
		mvn -N -Dmaven.version.rules="${rules}" versions:display-parent-updates
	;;
	update-parent)
		mvn -N -Dmaven.version.rules="${rules}" versions:update-parent
	;;
	*)
		echo "Unknown command: [$1]"
		exit 1
	;;
esac
