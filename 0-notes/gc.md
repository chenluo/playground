# GC

## heap generation model

eden : survival1 : survival2 : tenured(old)

## GC Collectors

* serial:
  only for small memory
* parallel:
  High thoughput
* concurrent: CMS/G1/ZGC
  Less stop time