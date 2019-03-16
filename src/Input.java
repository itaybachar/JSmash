import java.nio.ByteBuffer;
import static org.lwjgl.glfw.GLFW.*;

public class Input {
	public static final int	A	= 0;
	public static final int	B	= 1;
	public static final int	X	= 2;
	public static final int	Y	= 3;
	public static final int	LB	= 4;
	public static final int	RB	= 5;
	public static final int	BACK	= 6;
	public static final int	MENU	= 7;
	public static final int	LJOY_IN	= 8;
	public static final int	RJOY_IN	= 9;
	public static final int	D_UP	= 10;
	public static final int	D_RIGHT	= 11;
	public static final int	D_DOWN	= 12;
	public static final int	D_LEFT	= 13;
	
	public Input()
	{
		// TODO: Implement active controllers/remapping
	}
	
	public int getButton(int id)
	{
		ByteBuffer buttonStates;
		try {
			if (id < 0 || id > 15)
				throw new RuntimeException("Controller indexes are 0-15");
			
			buttonStates = glfwGetJoystickButtons(id);
			for (int i = 0; i < buttonStates.capacity(); ++i)
				if (buttonStates.get(i) == 1)
					return i;
			return -1;
		} catch (Exception e) {
			throw e;
		}
	}
}
