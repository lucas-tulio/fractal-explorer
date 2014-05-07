slick-fractal
=============

Prepare to see amazing shit like this on your computer screen:

![screenshot](https://raw.github.com/lucasdnd/slickFractal/master/fractal.png)

Fractal renderer in Java using lwjgl and Slick2D. Also using PixelData by davedes.

Very special thanks to lode.vandevenne and his tutorials on fractals, which allowed me to code this. Check out his tutorials here: http://lodev.org/cgtutor/juliamandelbrot.html

## How to run

Import the project into Eclipse and click Run. If you still want to do it from the command line, here's how:

1. Compile everything
2. Go to the bin dir
3. Use the following command:

$ java -cp .:../lib/slick.jar:../lib/lwjgl.jar -Djava.library.path=../lib/natives/ main/Start

## Important

If you're going to run it on fullscreen, make sure you select a resolution your display supports.

## Cool things to do

When rendering the Julia Set, try these values:

c = -1.037
i = 0.17

c = -0.8
i = 0.156
