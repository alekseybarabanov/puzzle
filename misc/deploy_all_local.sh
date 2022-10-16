#!/bin/bash

bash deploy_puzzle_repository_local.sh &
sleep 10
bash deploy_puzzle_assembler_local.sh &
sleep 10
bash deploy_puzzle_api_local.sh &
