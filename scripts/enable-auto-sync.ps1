[CmdletBinding()]
param()

$ErrorActionPreference = 'Stop'
$root = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
& git -C $root config core.hooksPath .githooks
if ($LASTEXITCODE -ne 0) {
    throw 'Unable to enable the repository-local Git hooks path.'
}

Write-Output 'status=auto_sync_enabled'
Write-Output 'contract=successful_local_commit_pushes_origin;failed_push_preserves_local_commit'
