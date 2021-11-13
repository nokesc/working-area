#!/bin/bash

usage() {
    >&2 echo "Usage: $0 [# th:each="p : ${parameters}"]
    [(${p.name})]: [(${p.description})]  
[/]"
}

case in $1:[# th:each="p : ${parameters}"]
    ${{p.name}} [${p.name}] [[${p.name}]] [(${p.name})]  
[/]
esac