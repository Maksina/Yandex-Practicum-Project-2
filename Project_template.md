## Изучите [README.md](README.md) файл и структуру проекта.

## Задание 1

*Примечание*: Сервис events не используется в целевой архитектуре, т.к. является анти-паттерном из-за широкой ответственности. Поэтому данный сервис далее будет использоваться только для тестирования гипотизы из задания 2.2, но в целевой архитектуре использовать не будет.

[Диаграмма контейнеров](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/schemas/to-be-containers.plantuml)

![Image Диаграмма контейнеров](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/schemas/to-be-containers.png)

## Задание 2

### 1. Proxy

Реализовано с использованием Spring Cloud Gateway.

### 2. Kafka

**Результаты тестов**  

[Результаты тестов](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-2/tests.png)

![Image Результаты тестов](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-2/tests.png)

**Состояние топиков**  

[Состояние топиков](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-2/topics.png)

![Image Состояние топиков](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-2/topics.png)


## Задание 3

### CI/CD

[CI/CD](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-3/ci-cd.png)

![Image CI/CD](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-3/ci-cd.png)

### Proxy в Kubernetes

#### Шаг 1

#### Шаг 2

**Логи event-service**

[Логи event-service](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-3/3-2-12-events-log.png)

![Image Логи event-service](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-3/3-2-12-events-log.png)

**Прохождение тестов npm run test:kubernetes**

[Прохождение тестов npm run test:kubernetes](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-3/3-2-12-test-events.png)

![Image Прохождение тестов npm run test:kubernetes](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-3/3-2-12-test-events.png)

#### Шаг 3

**Вызов https://cinemaabyss.example.com/api/movies из браузера**

[Вызов https://cinemaabyss.example.com/api/movies из браузера](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-3/3-3-movies-browser.png)

![Image Вызов https://cinemaabyss.example.com/api/movies из браузера](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-3/3-3-movies-browser.png)

**Вызов https://cinemaabyss.example.com/api/movies из Postman**

[Вызов https://cinemaabyss.example.com/api/movies из Postman](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-3/3-3-movies-postman.png)

![Image Вызов https://cinemaabyss.example.com/api/movies из Postman](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-3/3-3-movies-postman.png)

**Вывод event-service после тестов**

[Вывод event-service после тестов](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-3/3-3-events-log.png)

![Image Вывод event-service после тестов](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-3/3-3-events-log.png)

## Задание 4

**Запущенный k8s и вызов moviesSerivce**

[Запущенный k8s и вызов moviesSerivce](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-4/helm.png)

![Image Запущенный k8s и вызов moviesSerivce](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-4/helm.png)

# Задание 5
Компания планирует активно развиваться и для повышения надежности, безопасности, реализации сетевых паттернов типа Circuit Breaker и канареечного деплоя вам как архитектору необходимо развернуть istio и настроить circuit breaker для monolith и movies сервисов.

```bash

helm repo add istio https://istio-release.storage.googleapis.com/charts
helm repo update

helm install istio-base istio/base -n istio-system --set defaultRevision=default --create-namespace
helm install istio-ingressgateway istio/gateway -n istio-system
helm install istiod istio/istiod -n istio-system --wait

helm install cinemaabyss .\src\kubernetes\helm --namespace cinemaabyss --create-namespace

kubectl label namespace cinemaabyss istio-injection=enabled --overwrite

kubectl get namespace -L istio-injection

kubectl apply -f .\src\kubernetes\circuit-breaker-config.yaml -n cinemaabyss

```

Тестирование

# fortio
```bash
kubectl apply -f https://raw.githubusercontent.com/istio/istio/release-1.25/samples/httpbin/sample-client/fortio-deploy.yaml -n cinemaabyss
```

# Get the fortio pod name
```bash
FORTIO_POD=$(kubectl get pod -n cinemaabyss | grep fortio | awk '{print $1}')

kubectl exec -n cinemaabyss $FORTIO_POD -c fortio -- fortio load -c 50 -qps 0 -n 500 -loglevel Warning http://movies-service:8081/api/movies
```
Например,

```bash
kubectl exec -n cinemaabyss fortio-deploy-b6757cbbb-7c9qg  -c fortio -- fortio load -c 50 -qps 0 -n 500 -loglevel Warning http://movies-service:8081/api/movies
```

Вывод будет типа такого

```bash
IP addresses distribution:
10.106.113.46:8081: 421
Code 200 : 79 (15.8 %)
Code 500 : 22 (4.4 %)
Code 503 : 399 (79.8 %)
```
Можно еще проверить статистику

```bash
kubectl exec -n cinemaabyss fortio-deploy-b6757cbbb-7c9qg -c istio-proxy -- pilot-agent request GET stats | grep movies-service | grep pending
```

И там смотрим 

```bash
cluster.outbound|8081||movies-service.cinemaabyss.svc.cluster.local;.upstream_rq_pending_total: 311 - столько раз срабатывал circuit breaker
You can see 21 for the upstream_rq_pending_overflow value which means 21 calls so far have been flagged for circuit breaking.
```

Приложите скриншот работы circuit breaker'а

Удаляем все
```bash
istioctl uninstall --purge
kubectl delete namespace istio-system
kubectl delete all --all -n cinemaabyss
kubectl delete namespace cinemaabyss
```
