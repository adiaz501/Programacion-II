from abc import ABC, abstractmethod

class Empleado(ABC):
    def __init__(self, nombre):
        self.nombre = nombre
    @abstractmethod
    def CalcularSalarioMensual(self):
        pass

    def toString(self):
        return f"Empleado: {self.nombre}"

class EmpleadoTiempoCompleto(Empleado):
    def __init__(self, nombre, salario_anual):
        super().__init__(nombre)
        self.salario_anual = salario_anual

    def CalcularSalarioMensual(self):
        return self.salario_anual / 12

    def toString(self):
        return f"Empleado tiempo completo: {self.nombre}, Salario mensual: Bs. {self.CalcularSalarioMensual():.2f}"


class EmpleadoTiempoHorario(Empleado):
    def __init__(self, nombre, horas_trabajadas, tarifa_por_hora):
        super().__init__(nombre)
        self.horas_trabajadas = horas_trabajadas
        self.tarifa_por_hora = tarifa_por_hora

    def CalcularSalarioMensual(self):
        return self.horas_trabajadas * self.tarifa_por_hora

    def toString(self):
        return  f"Empleado tiempo horario: {self.nombre}, Salario Mensual: Bs. {self.CalcularSalarioMensual():.2f}"


def main():
    print("------------------")
    empleados = []
    print("Empleados tiempo completo")

    for i in range(3):
        nombre = input("Nombre: ")
        salario_anual = float(input("Salario anual: "))
        empleado = EmpleadoTiempoCompleto(nombre, salario_anual)
        empleados.append(empleado)

    print("Empleados tiempo horario")

    for i in range(2):
        nombre = input("Nombre: ")
        horas_trabajadas = float(input("Horas trabajadas: "))
        tarifa_por_hora = float(input("Tarifa por hora: "))
        empleado = EmpleadoTiempoHorario(nombre, horas_trabajadas, tarifa_por_hora)
        empleados.append(empleado)

    print("-----------------------")
    total_salario = 0

    for empleado in empleados:
        print(empleado.toString())

if __name__ == "__main__":
    main()








