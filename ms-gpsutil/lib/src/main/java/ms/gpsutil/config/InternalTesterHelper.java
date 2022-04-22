package ms.gpsutil.config;

public class InternalTesterHelper {

    private static int internalUserNumber = 100;

    public static void setInternalUserNumber(int internalUserNumber) {
	InternalTesterHelper.internalUserNumber = internalUserNumber;
    }

    public static int getInternalUserNumber() {
	return internalUserNumber;
    }

}
