import static org.lwjgl.opengl.GL30.*;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ShaderProgram {
	int programID;
	int vertexShaderID;
	int fragmentShaderID;
	
	public ShaderProgram()
	{
		programID = glCreateProgram();
	}

	public void attachVertexShader(String name)
	{
		String vertexShaderSource = readFromFile(name);
		
		vertexShaderID = glCreateShader(GL_VERTEX_SHADER);
		glShaderSource(vertexShaderID, vertexShaderSource);
		
		glCompileShader(vertexShaderID);
		
		// Check for errors
		if (glGetShaderi(vertexShaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new RuntimeException("Error creating vertex shader\n"
					+ glGetShaderInfoLog(vertexShaderID,
							glGetShaderi(vertexShaderID, GL_INFO_LOG_LENGTH)));
		
		glAttachShader(programID, vertexShaderID);
	}
	
	public void attachFragmentShader(String name)
	{
		String fragmentShaderSource = readFromFile(name);
		
		fragmentShaderID = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(fragmentShaderID, fragmentShaderSource);
		
		glCompileShader(fragmentShaderID);
		
		// Check for errors
		if (glGetShaderi(fragmentShaderID, GL_COMPILE_STATUS) == GL_FALSE)
			throw new RuntimeException("Error creating fragment shader\n"
					+ glGetShaderInfoLog(fragmentShaderID, glGetShaderi(
							fragmentShaderID, GL_INFO_LOG_LENGTH)));
		
		glAttachShader(programID, fragmentShaderID);
	}
	
	public void link()
	{
		glLinkProgram(programID);
		
		if (glGetProgrami(programID, GL_LINK_STATUS) == GL_FALSE)
			throw new RuntimeException("Unable to link shader program:");
	}
	
	public void bind()
	{
		glUseProgram(programID);
	}
	
	public static void unbind()
	{
		glUseProgram(0);
	}
	
	
	public int getID()
	{
		return programID;
	}
	
	public void setFloat(String name, float value)
	{
		glUniform1f(glGetUniformLocation(programID, name), value);
	}

	public void setInt(String name, int value)
	{
		glUniform1f(glGetUniformLocation(programID, name), value);
	}
	
	public void setBool(String name, boolean value)
	{
		this.setInt(name, value ? 1 : 0);
	}
	
	public static String readFromFile(String name)
	{
		StringBuilder source = new StringBuilder();
		try {
			BufferedReader reader = new BufferedReader(
					new InputStreamReader(ShaderProgram.class.getClassLoader()
							.getResourceAsStream(name)));
			
			String line;
			while ((line = reader.readLine()) != null) {
				source.append(line).append("\n");
			}
			
			reader.close();
		} catch (Exception e) {
			System.err.println("Error loading source code: " + name);
			e.printStackTrace();
		}
		
		return source.toString();
	}
	
	public void dispose()
	{
		unbind();
		
		glDetachShader(programID, vertexShaderID);
		glDetachShader(programID, fragmentShaderID);
		
		glDeleteShader(vertexShaderID);
		glDeleteShader(fragmentShaderID);
		
		glDeleteProgram(programID);
	}
}