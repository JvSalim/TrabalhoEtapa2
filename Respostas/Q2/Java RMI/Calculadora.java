import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculadora extends Remote {
    double somar(double a, double b) throws RemoteException;
    double subtrair(double a, double b) throws RemoteException;
    double multiplicar(double a, double b) throws RemoteException;
    double dividir(double a, double b) throws RemoteException;
}
