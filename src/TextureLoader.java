import static org.lwjgl.opengl.GL30.*;

public class TextureLoader {
	
	private int texID;
	
	public TextureLoader(ImageLoader img)
	{
		texID = glGenTextures();
		glBindTexture(GL_TEXTURE_2D, texID);
		
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);	
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		
		if (img.data != null) {
			glTexImage2D(GL_TEXTURE_2D, 0, GL_RGB, img.width, img.height, 0, GL_RGB,
					GL_UNSIGNED_BYTE, img.data);
			glGenerateMipmap(GL_TEXTURE_2D);
		} else {
			System.out.println("Failed to load texture.");
		}
		img.free();
	}
	
	public void bind()
	{
		glBindTexture(GL_TEXTURE_2D, texID);
	}
}
