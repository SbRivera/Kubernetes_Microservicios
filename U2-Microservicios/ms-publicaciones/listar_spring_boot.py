import os
from datetime import datetime

def listar_arbol_carpeta(ruta, prefijo="", nivel=0):
    """
    Lista el contenido de una carpeta en formato de árbol, excluyendo target y .class.
    """
    try:
        # Obtener contenido de la carpeta actual (ordenado alfabéticamente)
        elementos = sorted(os.listdir(ruta))
        # Filtrar elementos para excluir target y .class
        elementos = [e for e in elementos if e != "target" and not e.endswith(".class")]
        
        for i, elemento in enumerate(elementos):
            ruta_elemento = os.path.join(ruta, elemento)
            # Calcular prefijo para la visualización del árbol
            es_ultimo = i == len(elementos) - 1
            prefijo_rama = prefijo + ("└── " if es_ultimo else "├── ")
            prefijo_subnivel = prefijo + ("    " if es_ultimo else "│   ")
            
            if os.path.isfile(ruta_elemento):
                # Es un archivo
                stats = os.stat(ruta_elemento)
                tamano = stats.st_size
                fecha_mod = datetime.fromtimestamp(stats.st_mtime).strftime('%Y-%m-%d %H:%M:%S')
                print(f"{prefijo_rama}📄 {elemento} ")
            elif os.path.isdir(ruta_elemento):
                # Es una carpeta
                print(f"{prefijo_rama}📁 {elemento}")
                # Recursión para subcarpetas
                listar_arbol_carpeta(ruta_elemento, prefijo_subnivel, nivel + 1)

    except PermissionError:
        print(f"{prefijo}Error: No tienes permisos para acceder a {ruta}")
    except Exception as e:
        print(f"{prefijo}Error al explorar {ruta}: {e}")

# Ruta específica de la carpeta src
ruta_src = r"C:\Users\HP\Documents\Distribuidas\Taller_RabitMQ\Taller_RabitMQ\Publicaciones\Publicaciones\publicaciones\src"

# Verificar si la carpeta existe
if os.path.exists(ruta_src):
    print(f"Explorando la carpeta: {ruta_src}\n")
    print(f"📁 src")
    listar_arbol_carpeta(ruta_src)
else:
    print(f"La carpeta {ruta_src} no existe. Verifica la ruta o especifica si usas src/main.")