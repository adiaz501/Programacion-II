from abc import ABC, abstractmethod


class Animal(ABC):
    @abstractmethod
    def sonido(self):
        pass

    @abstractmethod
    def comoSeDesplaza(self):
        pass


class Perro(Animal):
    def sonido(self):
        return "¡Guau!"

    def comoSeDesplaza(self):
        return "Camina, corre o salta"


class Serpiente(Animal):
    def sonido(self):
        return "¡Sssss!"

    def comoSeDesplaza(self):
        return "Se arrastra por el suelo"


class Gato(Animal):
    def sonido(self):
        return "¡Miau!"

    def comoSeDesplaza(self):
        return "Camina sigilosamente y salta"


class Pajaro(Animal):
    def sonido(self):
        return "¡Pío!"

    def comoSeDesplaza(self):
        return "Vuela por el aire"


class Pez(Animal):
    def sonido(self):
        return "¡Blub!"

    def comoSeDesplaza(self):
        return "Nada en el agua"


animales = [Perro(), Serpiente(), Gato(), Pajaro(), Pez()]

for a in animales:
    print(f"{a.__class__.__name__} sonido: {a.sonido()}")
    print(f"{a.__class__.__name__} se desplaza: {a.comoSeDesplaza()}")
    print("-" * 40)