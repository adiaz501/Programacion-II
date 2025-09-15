import math
class Vecto3D
    def __init__(self, a1, a2, a3):
        self.a1 = a1
        self.a2 = a2
        self.a3 = a3

    def __str__(self):
        return f"Vector({self.a1:.2f}, {self.a2:.2f}, {self.a3:.2f})"

    def __add__(self, o):
        return Vector(self.a1 + o.a1, self.a2 + o.a2, self.a3 + o.a3)

    def __mul__(self, r):
        return Vector(self.a1 * r, self.a2 * r, self.a3 * r)

    def __rmul__(self, r):
        return self.__mul__(r)

    def __abs__(self):
        return math.sqrt(self.a1**2 + self.a2**2 + self.a3**2)

    def unit(self):
        mag = abs(self)
        if mag == 0:
            return Vector(0, 0, 0)  # se devuelve vector nulo
        return Vector(self.a1 / mag, self.a2 / mag, self.a3 / mag)

    def producto_escalar(self, o):
        return self.a1 * o.a1 + self.a2 * o.a2 + self.a3 * o.a3

    def __matmul__(self, o):
        return self.producto_vectorial(o)

    def producto_vectorial(self, o):
        a1 = self.a2 * o.a3 - self.a3 * o.a2
        a2 = self.a3 * o.a1 - self.a1 * o.a3
        a3 = self.a1 * o.a2 - self.a2 * o.a1
        return Vector(a1, a2, a3)

    def __eq__(self, o):
        return (self.a1, self.a2, self.a3) == (o.a1, o.a2, o.a3)


print("--- Vectores ---")
a = Vector(1, 2, 3)
b = Vector(4, 5, 6)
print(f"Vector a: {a}")
print(f"Vector b: {b}")

print("\n--- a) Suma de vectores (a + b) ---")
print("a + b =", a + b)

print("\n--- b) Multiplicación escalar (r * a) ---")
r = 2
print(f"{r} * a =", r * a)

print("\n--- c) Longitud de un vector (|a|) ---")
print("|a| =", round(abs(a), 2))

print("\n--- d) Normal del vector (a.unit()) ---")
print("Normal de a =", a.unit())

print("\n--- e) Producto escalar ---")
print("a . b =", a.producto_escalar(b))

print("\n--- f) Producto vectorial ---")
print("a x b =", a @ b)
print("a x b =", a.producto_vectorial(b))
