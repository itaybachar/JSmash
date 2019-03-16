import java.nio.ByteBuffer;
import static org.lwjgl.stb.STBImage.*;

public class ImageLoader {
	
	public int width, height, nrChannels;
	public ByteBuffer data;
	
	public ImageLoader(String name)
	{
		int[] x = new int[1];
		int[] y = new int[1];
		int[] c = new int[1];
		
		stbi_set_flip_vertically_on_load(true);
		data = stbi_load(name, x, y, c, 0);
		
		width = x[0];
		height = y[0];
	}
	
	public void free()
	{
		stbi_image_free(data);
	}
}
