# 🧱 Microservicios con Kubernetes y Docker

Este proyecto implementa una arquitectura de microservicios en Kubernetes, usando múltiples servicios como autenticación, catálogo, notificaciones, sincronización, gateway API, Eureka y una base de datos distribuida con CockroachDB, además de mensajería con RabbitMQ.

## 🚀 Servicios Incluidos

- **AuthService**: Servicio de autenticación y generación de tokens JWT.
- **API Gateway**: Entrada única para todas las peticiones hacia los microservicios.
- **Catalogo**: Servicio de gestión de catálogo de elementos.
- **Eureka Server**: Registro y descubrimiento de servicios.
- **Notificaciones**: Servicio de envío de notificaciones.
- **Publicaciones**: Servicio de gestión de publicaciones.
- **Sincronización**: Servicio de coordinación y sincronización de procesos.
- **CockroachDB**: Base de datos distribuida, altamente disponible.
- **RabbitMQ**: Sistema de mensajería para comunicación asíncrona.

---

## 🐳 Construcción y envío de imágenes Docker

Asegúrate de reemplazar `<usuario>` con tu usuario de Docker Hub.

```bash
cd authservice
docker build -t <usuario>/authservice:latest .
docker push <usuario>/authservice:latest
cd ..

cd ms-api-gateway
docker build -t <usuario>/ms-api-gateway:latest .
docker push <usuario>/ms-api-gateway:latest
cd ..

cd ms-catalogo
docker build -t <usuario>/ms-catalogo:latest .
docker push <usuario>/ms-catalogo:latest
cd ..

cd ms-eureka-server
docker build -t <usuario>/ms-eureka-server:latest .
docker push <usuario>/ms-eureka-server:latest
cd ..

cd ms-notificaciones
docker build -t <usuario>/ms-notificaciones:latest .
docker push <usuario>/ms-notificaciones:latest
cd ..

cd ms-publicaciones
docker build -t <usuario>/ms-publicaciones:latest .
docker push <usuario>/ms-publicaciones:latest
cd ..

cd ms-sincronizacion
docker build -t <usuario>/ms-sincronizacion:latest .
docker push <usuario>/ms-sincronizacion:latest
cd ..
```

---

## ☸️ Despliegue en Kubernetes

### 1. Crear Namespace

```bash
kubectl apply -f app-publicaciones-ns.yml
```

### 2. Desplegar Base de Datos (CockroachDB)

```bash
kubectl apply -f cockroachdb-deployment.yaml
kubectl apply -f cockroachdb-service.yaml

kubectl exec -it <nombre_pod_bd> -- ./cockroach sql --insecure
CREATE DATABASE publicaciones_db;
CREATE DATABASE authdb;
CREATE DATABASE catalogo_db;
CREATE DATABASE notificaciones_db;

kubectl apply -f cockroachdb-node2-deployment.yaml
kubectl apply -f cockroachdb-node2-service.yaml
kubectl apply -f cockroachdb-node3-deployment.yaml
kubectl apply -f cockroachdb-node3-service.yaml
kubectl apply -f cockroachdb-node4-deployment.yaml
kubectl apply -f cockroachdb-node4-service.yaml
```

### 3. Desplegar RabbitMQ

```bash
kubectl apply -f rabbitmq-deployment.yaml
kubectl apply -f rabbitmq-service.yaml
```

### 4. Desplegar Eureka Server

```bash
kubectl apply -f ms-eureka-server-deployment.yml
kubectl apply -f ms-eureka-server-service.yml
```

### 5. Desplegar Microservicios

```bash
kubectl apply -f ms-notificaciones-deployment.yml
kubectl apply -f ms-notificaciones-service.yml

kubectl apply -f ms-publicaciones-deployment.yml
kubectl apply -f ms-publicaciones-service.yml

kubectl apply -f ms-catalogo-deployment.yml
kubectl apply -f ms-catalogo-service.yml

kubectl apply -f ms-sincronizacion-deployment.yml
kubectl apply -f ms-sincronizacion-service.yml

kubectl apply -f authservice-deployment.yml
kubectl apply -f authservice-service.yml
```

### 6. Desplegar API Gateway

```bash
kubectl apply -f api-gateway-config.yaml 
kubectl apply -f ms-api-gateway-deployment.yml
kubectl apply -f ms-api-gateway-service.yml
```

---

## 🧪 Verificación de funcionamiento

Una vez todos los pods estén corriendo:

```bash
kubectl get pods
kubectl port-forward svc/ms-api-gateway-svc 8000:8000
```

Visita en el navegador:

```
http://localhost:8000/swagger-ui/index.html
```

---

## 🔐 Prueba de autenticación

```bash
curl -X 'POST' \
  'http://localhost:8000/api/auth/login' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d '{
  "username": "admin",
  "password": "admin123"
}'
```

Respuesta esperada:

```json
{
  "token": "eyJh...<token JWT>...XQ"
}
```

---

## 🧾 Requisitos

- Docker
- Kubernetes (minikube o cluster)
- `kubectl`
- CockroachDB CLI
- RabbitMQ
- Java 17+
- Maven o Gradle (dependiendo del proyecto)

---

## 🧠 Notas adicionales

- Todos los servicios están registrados en Eureka.
- La comunicación entre servicios se da vía REST y/o eventos con RabbitMQ.
- La persistencia está gestionada por CockroachDB.

---

> Proyecto desarrollado como ejemplo educativo de arquitectura de microservicios orquestada con Kubernetes.
