#!/bin/bash

declare project_dir=$(dirname "$0")
declare dc_infra=${project_dir}/docker/docker-compose-infra.yml
function start() {
    echo 'Starting Infra Stack....'
    docker compose -f "${dc_infra}" up --build --force-recreate -d
}

function stop() {
    echo 'Stopping Infra Stack....'
    docker compose -f "${dc_infra}" stop
    docker compose -f "${dc_infra}" rm -f
}

action="start"

if [[ "$#" != "0"  ]]
then
    action=$*
fi

eval "${action}"