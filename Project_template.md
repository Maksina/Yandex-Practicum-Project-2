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

Команда начала переезд в Kubernetes для лучшего масштабирования и повышения надежности. 
Вам, как архитектору осталось самое сложное:
 - реализовать CI/CD для сборки прокси сервиса
 - реализовать необходимые конфигурационные файлы для переключения трафика.


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
Для простоты дальнейшего обновления и развертывания вам как архитектуру необходимо так же реализовать helm-чарты для прокси-сервиса и проверить работу 

Для этого:
1. Перейдите в директорию helm и отредактируйте файл values.yaml

```yaml
# Proxy service configuration
proxyService:
  enabled: true
  image:
    repository: ghcr.io/db-exp/cinemaabysstest/proxy-service
    tag: latest
    pullPolicy: Always
  replicas: 1
  resources:
    limits:
      cpu: 300m
      memory: 256Mi
    requests:
      cpu: 100m
      memory: 128Mi
  service:
    port: 80
    targetPort: 8000
    type: ClusterIP
```

- Вместо ghcr.io/db-exp/cinemaabysstest/proxy-service напишите свой путь до образа для всех сервисов
- для imagePullSecret проставьте свое значение (скопируйте из конфигурации kubernetes)
  ```yaml
  imagePullSecrets:
      dockerconfigjson: ewoJImF1dGhzIjogewoJCSJnaGNyLmlvIjogewoJCQkiYXV0aCI6ICJaR0l0Wlhod09tZG9jRjl2UTJocVZIa3dhMWhKVDIxWmFVZHJOV2hRUW10aFVXbFZSbTVaTjJRMFNYUjRZMWM9IgoJCX0KCX0sCgkiY3JlZHNTdG9yZSI6ICJkZXNrdG9wIiwKCSJjdXJyZW50Q29udGV4dCI6ICJkZXNrdG9wLWxpbnV4IiwKCSJwbHVnaW5zIjogewoJCSIteC1jbGktaGludHMiOiB7CgkJCSJlbmFibGVkIjogInRydWUiCgkJfQoJfSwKCSJmZWF0dXJlcyI6IHsKCQkiaG9va3MiOiAidHJ1ZSIKCX0KfQ==
  ```

2. В папке ./templates/services заполните шаблоны для proxy-service.yaml и events-service.yaml (опирайтесь на свою kubernetes конфигурацию - смысл helm'а сделать шаблоны для быстрого обновления и установки)

```yaml
template:
    metadata:
      labels:
        app: proxy-service
    spec:
      containers:
       Тут ваша конфигурация
```

3. Проверьте установку
Сначала удалим установку руками

```bash
kubectl delete all --all -n cinemaabyss
kubectl delete  namespace cinemaabyss
```
Запустите 
```bash
helm install cinemaabyss .\src\kubernetes\helm --namespace cinemaabyss --create-namespace
```
Если в процессе будет ошибка
```code
[2025-04-08 21:43:38,780] ERROR Fatal error during KafkaServer startup. Prepare to shutdown (kafka.server.KafkaServer)
kafka.common.InconsistentClusterIdException: The Cluster ID OkOjGPrdRimp8nkFohYkCw doesn't match stored clusterId Some(sbkcoiSiQV2h_mQpwy05zQ) in meta.properties. The broker is trying to join the wrong cluster. Configured zookeeper.connect may be wrong.
```

Проверьте развертывание:
```bash
kubectl get pods -n cinemaabyss
minikube tunnel
```

Потом вызовите 
https://cinemaabyss.example.com/api/movies
и приложите скриншот развертывания helm и вывода https://cinemaabyss.example.com/api/movies


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
