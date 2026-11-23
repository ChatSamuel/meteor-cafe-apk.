$ErrorActionPreference = 'Stop'
$Root = Split-Path -Parent $PSScriptRoot
$Target = Join-Path $Root 'app/src/main/cpp/mmapi_tsf/TinySoundFont'
$Header = Join-Path $Target 'tsf.h'
if (Test-Path $Header) {
    Write-Host "TinySoundFont já está presente em: $Target"
    exit 0
}
$Parent = Split-Path -Parent $Target
New-Item -ItemType Directory -Force -Path $Parent | Out-Null
git clone --depth 1 https://github.com/schellingb/TinySoundFont.git $Target
if (-not (Test-Path $Header)) { throw 'O clone terminou, mas tsf.h não foi encontrado.' }
Write-Host 'TinySoundFont restaurado. Preserve o arquivo LICENSE do projeto.'
