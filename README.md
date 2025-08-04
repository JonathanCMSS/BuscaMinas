# Buscaminas - Programación Orientada a Objetos

## 📋 Descripción del Proyecto

Implementación del clásico juego **Buscaminas** en consola, desarrollado como proyecto final para la asignatura de **Programación Orientada a Objetos** de la Universidad Politécnica Salesiana.

Este proyecto demuestra la aplicación práctica de conceptos avanzados de POO incluyendo herencia, polimorfismo, encapsulamiento, patrón MVC, manejo de excepciones y persistencia de datos.

## Características del Juego

- **Tablero**: 10x10 casillas
- **Minas**: 15 minas distribuidas aleatoriamente
- **Sistema de coordenadas**: A-J (filas) y 1-10 (columnas)
- **Funcionalidades**:
  - ✅ Revelar casillas
  - ✅ Marcar/desmarcar casillas sospechosas
  - ✅ Expansión automática de zonas seguras
  - ✅ Contador de minas restantes
  - ✅ Sistema de tiempo
  - ✅ Estadísticas del jugador
  - ✅ Guardar/cargar partidas

## Arquitectura del Proyecto

### Estructura de Directorios
```
src/
├── controller/
│   └── ControladorJuego.java       # Controlador principal (MVC)
├── model/
│   ├── ElementoJuego.java          # Clase abstracta base
│   ├── CeldaJuego.java            # Representación de cada casilla
│   ├── TableroBuscaminas.java     # Lógica del tablero
│   ├── Jugador.java               # Gestión del jugador
│   └── Persistible.java           # Interface para persistencia
├── view/
│   └── VistaConsola.java          # Interfaz de usuario
├── exceptions/
│   ├── CasillaReveladaException.java
│   ├── CoordenadaFueraDeRangoException.java
│   ├── FormatoEntradaException.java
│   └── JuegoFinalizadoException.java
├── test/
│   └── TestCeldaJuego.java        # Pruebas unitarias
└── JuegoBuscaMinas.java           # Clase principal
```

### Patrón MVC Implementado
- **Modelo**: Clases de datos y lógica de negocio
- **Vista**: `VistaConsola` - Interfaz de usuario
- **Controlador**: `ControladorJuego` - Coordinación del flujo

## Instalación y Ejecución

### Requisitos Previos
- **Java JDK 17** o superior
- **IDE** recomendado: IntelliJ IDEA, Eclipse, o VS Code

### Pasos de Instalación

1. **Clonar el repositorio**
   ```bash
   git clone https://github.com/JonathanCMSS/BuscaMinas.git
   cd buscaminas-poo
   ```

2. **Compilar el proyecto**
   ```bash
   javac -d out src/**/*.java
   ```

3. **Ejecutar el juego**
   ```bash
   java -cp out JuegoBuscaMinas
   ```

### Ejecución desde IDE
1. Importar el proyecto en tu IDE
2. Establecer `src/` como directorio fuente
3. Ejecutar la clase `JuegoBuscaMinas.java`

## Cómo Jugar

### Comandos Básicos
- **Revelar casilla**: `A5`, `B3`, `J10` (letra + número)
- **Marcar casilla**: `marcar A5`, `marcar B3`
- **Comandos especiales**:
  - `guardar` - Guardar partida actual
  - `ayuda` - Mostrar instrucciones
  - `salir` - Terminar juego

### Símbolos del Tablero
| Símbolo | Significado |
|---------|-------------|
| `■` | Casilla sin revelar |
| `⚑` | Casilla marcada |
| `☢` | Mina (al perder) |
| ` ` | Casilla vacía |
| `1-8` | Número de minas adyacentes |

### Ejemplo de Juego
```
   1 2 3 4 5 6 7 8 9 10
A |■|■|■|■|■|■|■|■|■|■|
B |■|■|2|1| |1|2|■|■|■|
C |■|■|1| | | |1|■|■|■|
D |■|■|1| | | |1|■|■|■|
E |■|■|1|1|1|1|1|■|■|■|
F |■|■|■|■|■|■|■|■|■|■|
G |■|■|■|■|■|■|■|■|■|■|
H |■|■|■|■|■|■|■|■|■|■|
I |■|■|■|■|■|■|■|■|■|■|
J |■|■|■|■|■|■|■|■|■|■|

Minas restantes: 12

Ingrese comando: B5
```

## Conceptos de POO Implementados

### 1. **Herencia**
```java
public abstract class ElementoJuego {
    public abstract char obtenerSimbolo();
}

public class CeldaJuego extends ElementoJuego {
    @Override
    public char obtenerSimbolo() { /* implementación */ }
}
```

### 2. **Polimorfismo**
- Método `obtenerSimbolo()` con comportamiento específico según estado de la celda

### 3. **Encapsulamiento**
- Atributos privados con métodos de acceso controlado
- Validación en setters y métodos públicos

### 4. **Interfaces**
- `Persistible` para objetos que pueden guardarse/cargarse

### 5. **Excepciones Personalizadas**
- 4 excepciones específicas del dominio del juego
- Manejo robusto de errores

## Pruebas Unitarias

El proyecto incluye pruebas unitarias siguiendo metodología **TDD**:

```bash
# Ejecutar pruebas
java -cp out test.TestCeldaJuego
```

### Casos de Prueba Cubiertos
-  Creación de celdas
-  Asignación de minas
-  Revelado de celdas
-  Marcado de casillas
-  Manejo de excepciones

## Persistencia de Datos

### Archivos Generados
- `partida.dat` - Estado de partida guardada
- `jugador.dat` - Estadísticas del jugador

### Datos Persistidos
- Estado completo del tablero
- Tiempo de inicio de partida
- Estadísticas históricas del jugador
- Mejor tiempo registrado

## Estadísticas del Jugador

El juego rastrea automáticamente:
- **Partidas jugadas**: Total de partidas iniciadas
- **Partidas ganadas**: Victorias conseguidas
- **Porcentaje de victoria**: Ratio de éxito
- **Mejor tiempo**: Tiempo récord en victoria
- **Última partida**: Fecha y hora de última sesión

## Equipo de Desarrollo

- **Jonathan Cristhian Macias Soledispa** 


## Tecnologías Utilizadas

- **Lenguaje**: Java 17+
- **Paradigma**: Programación Orientada a Objetos
- **Patrón de Diseño**: Modelo-Vista-Controlador (MVC)
- **Persistencia**: Serialización de objetos Java
- **Testing**: JUnit-style assertions

## Principios de Código Limpio Aplicados

- **DRY** (Don't Repeat Yourself): Reutilización de código
- **KISS** (Keep It Simple, Stupid): Simplicidad en diseño
- **SOLID**: Principios de diseño orientado a objetos
- **Nombres descriptivos**: Variables y métodos autoexplicativos
- **Funciones pequeñas**: Métodos con responsabilidad única

## Manejo de Errores

### Excepciones Implementadas
1. **CasillaReveladaException**: Intento de revelar casilla ya descubierta
2. **CoordenadaFueraDeRangoException**: Coordenadas inválidas
3. **JuegoFinalizadoException**: Acciones sobre juego terminado
4. **FormatoEntradaException**: Formato de entrada incorrecto


## Licencia

Este proyecto fue desarrollado con fines académicos para la Universidad Politécnica Salesiana.


---

**Universidad Politécnica Salesiana**  
*Programación Orientada a Objetos - 2025*
