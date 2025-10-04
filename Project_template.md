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

1. Настроен circuit-breaker-config.yaml
2. Выполнены тесты: kubectl exec -n cinemaabyss fortio-deploy-b6757cbbb-7c9qg  -c fortio -- fortio load -c 50 -qps 0 -n 500 -loglevel Warning http://movies-service:8081/api/movies

[Результат тестов](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-5/circuit-breaker-1.png)

![Image Результат тестов](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-5/circuit-breaker-1.png)

3. Проверена статистика: kubectl exec -n cinemaabyss fortio-deploy-b6757cbbb-7c9qg -c istio-proxy -- pilot-agent request GET stats | grep movies-service | grep pending

[Результат статистики](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-5/circuit-breaker-2.png)

![Image Результат статистики](https://github.com/Maksina/Yandex-Practicum-Project-2/blob/cinema/screenshots/task-5/circuit-breaker-2.png)