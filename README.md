# Pman en Java Swing

Juego estilo Pac-Man desarrollado con Java Swing. El jugador recorre el laberinto, obtiene 10 puntos por cada alimento y pierde una vida al tocar un fantasma. Cada partida terminada se guarda en un ranking binario con el nombre del jugador, la puntuacion y la fecha.

## Caracteristicas

- Laberinto construido con una matriz de caracteres.
- Movimiento con las flechas del teclado.
- Cuatro fantasmas con direcciones aleatorias.
- Tres vidas por partida.
- Reinicio despues de perder.
- Ranking de jugadores guardado en `data/scores.dat`.
- Tabla de las 10 mejores puntuaciones.
- Recursos graficos separados del codigo fuente.
- Configuracion lista para Visual Studio Code.


## Como ejecutar en Visual Studio Code

1. Abre en VS Code la carpeta completa `Pman`.
2. Espera a que la extension de Java termine de cargar.
3. Selecciona **Ejecutar Pman** si VS Code pregunta que configuracion usar.
4. Escribe el nombre del jugador.



## Como ejecutar desde Windows

Haz doble clic en:


run.bat


Tambien puedes usar la terminal de PowerShell:


.\run.bat


## Como ejecutar desde Linux o macOS


chmod +x run.sh
./run.sh


## Controles

| Tecla | Accion |
|---|---|
| Flecha arriba | Mover hacia arriba |
| Flecha abajo | Mover hacia abajo |
| Flecha izquierda | Mover hacia la izquierda |
| Flecha derecha | Mover hacia la derecha |
| `R` | Mostrar la tabla de puntuaciones |
| Cualquier tecla despues de perder | Reiniciar la partida |

## Reglas del juego

- Cada alimento vale **10 puntos**.
- El jugador comienza con **3 vidas**.
- Al tocar un fantasma se pierde una vida.
- Cuando se consumen todos los alimentos, el mapa vuelve a cargarse y la puntuacion continua.
- Cuando las vidas llegan a cero, la puntuacion se guarda automaticamente.

## Archivo binario de puntuaciones

Las puntuaciones se almacenan en:


data/scores.dat

El archivo se crea automaticamente al finalizar la primera partida. Se utiliza `ObjectOutputStream` para escribir una lista de objetos `ScoreEntry` y `ObjectInputStream` para leerla.

No abras `scores.dat` como si fuera un archivo de texto, porque su contenido es binario. Para ver la informacion correctamente, presiona `R` dentro del juego.

Para borrar completamente el ranking:

1. Cierra el juego.
2. Elimina `data/scores.dat`.
3. Ejecuta nuevamente el proyecto.

El archivo `scores.dat` esta ignorado por Git para evitar subir puntuaciones locales al repositorio.

## Como cambiar el mapa

El mapa esta en `src/com/pacman/game/Pman.java`, dentro del arreglo `tileMap`.

Simbolos disponibles:

| Simbolo | Elemento |
|---|---|
| `X` | Pared |
| `P` | Posicion inicial de Pac-Man |
| `b` | Fantasma azul |
| `o` | Fantasma naranja |
| `p` | Fantasma rosado |
| `r` | Fantasma rojo |
| Espacio | Alimento |
| `O` | Espacio vacio sin alimento |

Cada fila debe conservar exactamente 19 caracteres y el mapa debe tener 21 filas.

## Formas de hacer el juego mas dificil

### 1. Aumentar la velocidad general

En `Pman.java`, cambia:


gameLoop = new Timer(50, this);


Por ejemplo:


gameLoop = new Timer(35, this);


Un numero menor hace que el juego se actualice mas rapido.

### 2. Reducir las vidas

Cambia:


private int lives = 3;


Y tambien el valor usado dentro de `restartGame()`.

### 3. Agregar mas fantasmas

Añade mas letras `b`, `o`, `p` o `r` en el arreglo `tileMap`, cuidando que cada fila conserva 19 caracteres.

### 4. Crear niveles

Agrega varios arreglos de mapas y cambia al siguiente cuando `foods.isEmpty()` sea verdadero. En cada nivel puedes aumentar la velocidad del temporizador.

### 5. Inteligencia artificial mas avanzada

En lugar de escoger siempre una direccion aleatoria, calcula la distancia entre cada fantasma y Pac-Man. Una version mas avanzada puede usar busqueda BFS para encontrar el camino mas corto.

### 6. Usar las pildoras de poder

El proyecto ya incluye `powerFood.png` y `scaredGhost.png`. Puedes agregar un simbolo especial al mapa, activar un temporizador y permitir que Pac-Man coma fantasmas durante algunos segundos.

### 7. Agregar frutas y bonificaciones

Puedes utilizar `cherry.png` y `cherry2.png` para crear objetos temporales con 100, 250 o 500 puntos.

### 8. Agregar limite de tiempo

Crea un contador regresivo. Cuando llegue a cero, termina la partida aunque todavia queden vidas.

### 9. Crear combos

Aumenta los puntos cuando el jugador consume varios alimentos o fantasmas sin perder una vida.

### 10. Separar el comportamiento de cada fantasma

- Rojo: persigue directamente al jugador.
- Rosado: intenta adelantarse a la direccion del jugador.
- Azul: combina la posicion del jugador y del fantasma rojo.
- Naranja: persigue al jugador solo cuando esta lejos.

## Commits recomendados

Despues de copiar todos los archivos y comprobar que el juego funciona, abre la terminal en la carpeta del proyecto y ejecuta:


git init
git branch -M main

git add src resources data/.gitkeep
git commit -m "feat: organiza Pac-Man y agrega ranking binario"

git add .vscode run.bat run.sh .gitignore
git commit -m "chore: agrega configuracion de ejecucion para VS Code"

git add README.md
git commit -m "docs: agrega instrucciones y mejoras futuras"


Para conectar el proyecto con GitHub:


git remote add origin https://github.com/O-Torress/Pman
git push -u origin main




