import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Worker extends Remote {
    Object compute(Task t, Params p) throws RemoteException;
    boolean hasClassCode(String className) throws RemoteException;
    void storeClassCode(String className, byte[] t) throws RemoteException;
}
