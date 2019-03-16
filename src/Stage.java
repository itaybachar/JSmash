import java.nio.FloatBuffer;

import org.lwjgl.*;
import static org.lwjgl.opengl.GL30.*;

public class Stage {
	
	private double			width, height;
	private int				vbo	= 0;
	private int				vao	= 0;
	private int				ebo	= 0;
	private ShaderProgram	shaderProgram;
	
	// private float vertices[] = {
	// -1.0f, 1.0f, 1.0f, 1.0f, 1.0f, -1.0f, -1.0f,
	// -1.0f };
	float[] vertices = new float[] {
			// +0.0f, +540.0f, // Top coordinate
			-940.0f, +520.0f, // Top-right coordinate
			+940.0f, +520.0f, // Top-left coordinate
			-940.0f, -520.0f, // Bottom-left coordinate
			+940.0f, -520.0f // Bottom-right coordinate
	};
	
	int indices[] = {
			0, 1, 2, 1, 2, 3
	};
	
	public Stage()
	{
		shaderProgram = new ShaderProgram();
		shaderProgram.attachVertexShader("stage.vs");
		shaderProgram.attachFragmentShader("stage.fs");
		shaderProgram.link();
		
		vao = glGenVertexArrays();
		glBindVertexArray(vao);
		
		FloatBuffer verticesBuffer = BufferUtils
				.createFloatBuffer(vertices.length);
		verticesBuffer.put(vertices).flip();
		
		vbo = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vbo);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_STATIC_DRAW);
		
		// What order to use vertices (specified in indicies)
		ebo = glGenBuffers();
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

		// Describe how to interpret vertices from array
		glVertexAttribPointer(0, 2, GL_FLOAT, false, 0, 0);
		glBindVertexArray(0);
	}
	
	public void dispose()
	{
		// Dispose the program
		shaderProgram.dispose();
		
		// Dispose the vertex array
		glBindVertexArray(0);
		glDeleteVertexArrays(vao);
		
		// Dispose the buffer object
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glDeleteBuffers(vbo);
	}
	
	public void render()
	{
		// glClear(GL_COLOR_BUFFER_BIT);
		glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		
		shaderProgram.bind();
		
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
//		glDrawArrays(GL_TRIANGLES, 0, 6);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glBindVertexArray(0);
		
		ShaderProgram.unbind();
		
	}
}
