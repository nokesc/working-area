#!/bin/bash -e

usage() {
    >&2 echo "Usage: $0 <command>
    
    Commands:{% for c in commands %}
        {{ c.name }} {% endfor %}

    Parameters:{% for p in parameters %}
        {{ p.name }}: {{ p.description }}{% endfor %}
"
}

case in $1:{% for c in commands %}
    {{ c.name }})

    ;;{% endfor %}
esac
