# ODESolver
JavaFX app that solves ordinary differential equations and visualise solutions.

### Section links

|[General](#general)|[Demo](#demo)|[Version history](#version-history)|[Feedback](#feedback)|
|:-----------------:|:-----------:|:---------------------------------:|:-------------------:|
___
### General
Application solves first-order ODE with Euler method and supports its forward and midpoint variants. 
To solve ODE it needs:
  - ODE in function f(t,x) form, 
  - compartment [a, b] over which the equation needs to be solved,
  - step size h,
  - initial condition x0.

Application puts solutions in form of t, x points into table and visualize them on t(x) scatter plot.
It supports saving calculated points in declared text file.

Application also have user friendly error handling.

### Demo
Below I present a video showing an example of using the application.

https://user-images.githubusercontent.com/95027426/175818157-1321a454-0546-4d18-8b02-aa6135a4eea2.mp4

### Version history
**1.0.1**

- updated .gitignore file
- minor performance update

**1.0.0 (Base app)**

- solving first-order ordinary differential equations
- supported methods: Euler forward and Euler midpoint
- visualisation on table and scatter plot
- saving table values

### Feedback
If you have any questions or comments regarding the app, please place them in Discussion panel or contact me directly.
