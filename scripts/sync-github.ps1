[CmdletBinding()]
param()

$ErrorActionPreference = 'Stop'
$root = (Resolve-Path (Join-Path $PSScriptRoot '..')).Path
$status = & git -C $root status --porcelain
if ($LASTEXITCODE -ne 0) {
    throw 'Unable to inspect repository state.'
}
if ($status) {
    throw 'Refusing to sync: commit or discard the working-tree changes first.'
}

& git -C $root fetch --prune origin
if ($LASTEXITCODE -ne 0) {
    throw 'Fetch failed; the local repository was not changed.'
}
& git -C $root rebase origin/main
if ($LASTEXITCODE -ne 0) {
    throw 'Rebase needs attention; no force-push was attempted.'
}
& git -C $root push origin main
if ($LASTEXITCODE -ne 0) {
    throw 'Push failed; local commits remain intact.'
}

Write-Output 'status=synchronized'
