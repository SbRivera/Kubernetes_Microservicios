# 🧱 Microservicios con Kubernetes y Docker

Este proyecto implementa una arquitectura de microservicios en Kubernetes, usando múltiples servicios como autenticación, catálogo, notificaciones, sincronización, gateway API, Eureka y una base de datos distribuida con CockroachDB, además de mensajería con RabbitMQ.

## Prerrequisitos

- Docker instalado y configurado con una cuenta en Docker Hub.
- Clúster de Kubernetes (por ejemplo, Minikube, Kind o un proveedor en la nube como GKE, EKS o AKS).
- `kubectl` configurado para interactuar con tu clúster de Kubernetes.
- Git instalado para clonar el repositorio.
- Acceso a una terminal con los permisos necesarios para ejecutar comandos de Docker, Git y Kubernetes.
- Reemplaza `<usuario>` con tu nombre de usuario de Docker Hub en los comandos a continuación.
- Reemplaza `<nombre pod bd>` con el nombre real del pod de CockroachDB cuando ejecutes comandos de base de datos.

## Instrucciones de Configuración y Despliegue

### 1. Clonar el Repositorio

Clona el repositorio desde GitHub para obtener todos los archivos necesarios.

```bash
git clone https://github.com/SbRivera/Kubernetes_Microservicios.git
cd U2-Microservicios
```

### 2. Construir y Publicar Imágenes Docker

Navega a cada directorio de servicio, construye las imágenes Docker y publícalas en Docker Hub.

```bash
# Servicio de Autenticación
cd authservice
docker build -t <usuario>/authservice:latest .
docker push <usuario>/authservice:latest
cd ..

# API Gateway
cd ms-api-gateway
docker build -t <usuario>/ms-api-gateway:latest .
docker push <usuario>/ms-api-gateway:latest
cd ..

# Servicio de Catálogo
cd ms-catalogo
docker build -t <usuario>/ms-catalogo:latest .
docker push <usuario>/ms-catalogo:latest
cd ..

# Eureka Server
cd ms-eureka-server
docker build -t <usuario>/ms-eureka-server:latest .
docker push <usuario>/ms-eureka-server:latest
cd ..

# Servicio de Notificaciones
cd ms-notificaciones
docker build -t <usuario>/ms-notificaciones:latest .
docker push <usuario>/ms-notificaciones:latest
cd ..

# Servicio de Publicaciones
cd ms-publicaciones
docker build -t <usuario>/ms-publicaciones:latest .
docker push <usuario>/ms-publicaciones:latest
cd ..

# Servicio de Sincronización
cd ms-sincronizacion
docker build -t <usuario>/ms-sincronizacion:latest .
docker push <usuario>/ms-sincronizacion:latest
cd ..
```

### 3. Crear Namespace de Kubernetes

Aplica la configuración del namespace para la aplicación.

```bash
kubectl apply -f .\app-publicaciones-ns.yml
```

### 4. Desplegar Clúster de CockroachDB e Inicializar Bases de Datos

Configura el clúster de CockroachDB y crea las bases de datos necesarias.

```bash
cd k8s

# Desplegar nodo principal de CockroachDB
kubectl apply -f cockroachdb-deployment.yaml
kubectl apply -f cockroachdb-service.yaml

# Inicializar bases de datos (reemplaza <nombre pod bd> con el nombre real del pod de CockroachDB)
kubectl exec -it <nombre pod bd> -- ./cockroach sql --insecure --host=cockroachdb-svc
```

Dentro del shell SQL de CockroachDB, ejecuta:

```sql
CREATE DATABASE publicaciones_db;
CREATE DATABASE authdb;
CREATE DATABASE catalogo_db;
CREATE DATABASE notificaciones_db;
```

Sal del shell SQL con `\q`.

Despliega nodos adicionales de CockroachDB para el clúster:

```bash
kubectl apply -f cockroachdb-node2-deployment.yaml
kubectl apply -f cockroachdb-node2-service.yaml
kubectl apply -f cockroachdb-node3-deployment.yaml
kubectl apply -f cockroachdb-node3-service.yaml
kubectl apply -f cockroachdb-node4-deployment.yaml
kubectl apply -f cockroachdb-node4-service.yaml
```

### 5. Desplegar RabbitMQ

Configura RabbitMQ para la mensajería entre microservicios.

```bash
kubectl apply -f rabbitmq-deployment.yaml
kubectl apply -f rabbitmq-service.yaml
```

### 6. Desplegar Eureka Server

Despliega el servidor de descubrimiento de servicios Eureka.

```bash
kubectl apply -f ms-eureka-server-deployment.yml
kubectl apply -f ms-eureka-server-service.yml
```

### 7. Desplegar Microservicios

Despliega los microservicios con sus respectivos despliegues y servicios.

```bash
# Servicio de Notificaciones
kubectl apply -f ms-notificaciones-deployment.yml
kubectl apply -f ms-notificaciones-service.yml

# Servicio de Publicaciones
kubectl apply -f ms-publicaciones-deployment.yml
kubectl apply -f ms-publicaciones-service.yml

# Servicio de Catálogo
kubectl apply -f ms-catalogo-deployment.yml
kubectl apply -f ms-catalogo-service.yml

# Servicio de Sincronización
kubectl apply -f ms-sincronizacion-deployment.yml
kubectl apply -f ms-sincronizacion-service.yml

# Servicio de Autenticación
kubectl apply -f authservice-deployment.yml
kubectl apply -f authservice-service.yml
```

### 8. Desplegar API Gateway

Despliega el API Gateway para enrutar solicitudes a los microservicios.

```bash
kubectl apply -f api-gateway-config.yaml
kubectl apply -f ms-api-gateway-deployment.yml
kubectl apply -f ms-api-gateway-service.yml
```

### 9. Acceder a la Aplicación

Reenvía el servicio de API Gateway para acceder a la aplicación localmente.

```bash
kubectl port-forward svc/ms-api-gateway-svc 8000:8000
```

Abre tu navegador y navega a:

```
http://localhost:8000/swagger-ui/index.html
```

Esto mostrará la interfaz de Swagger para interactuar con el API Gateway y probar los microservicios.

## Notas

- Asegúrate de que todos los recursos de Kubernetes (pods, servicios, etc.) estén en estado `Running` antes de acceder a la aplicación. Usa `kubectl get pods` y `kubectl get services` para verificar.
- Si encuentras problemas, revisa los logs con `kubectl logs <pod-name>` para depurar.
- Reemplaza `<usuario>` con tu nombre de usuario de Docker Hub en todos los comandos de Docker.
- Reemplaza `<nombre pod bd>` con el nombre real del pod de CockroachDB, que puedes encontrar usando `kubectl get pods`.
- La aplicación asume un clúster de Kubernetes configurado correctamente con suficientes recursos para ejecutar todos los servicios y el clúster de CockroachDB.

## Repositorio

[Repositorio de GitHub](https://github.com/SbRivera/Kubernetes_Microservicios)
