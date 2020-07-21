@echo off
title Testing Client...
timeout 20
gradlew runClient --offline
exit