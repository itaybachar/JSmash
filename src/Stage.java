import static org.lwjgl.opengl.GL30.*;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

public class Stage {
	
	private int		vbo;
	private int		vao;
	private int		ebo;
	private ShaderProgram	shaderProgram;
	private ImageLoader	imgLoader;
	private TextureLoader	texLoader;
	
	// stride = 8floats * 4bytes/float = 32 bytes
	private float[] vertices = new float[] {
			// positions        // colors         // texture coords		
			-1.0f, +1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, // Top-left
			+1.0f, +1.0f, 0.0f, 0.0f, 1.0f, 0.0f, 1.0f, 1.0f, // Top-right
			-1.0f, -1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, // Bottom-left
			+1.0f, -1.0f, 0.0f, 0.3f, 0.8f, 0.5f, 1.0f, 0.0f // Bottom-right
	};
	
	private int indices[] = {
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
		// Stride is in BYTES!!!
		glVertexAttribPointer(0, 3, GL_FLOAT, false, 32, 0);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(1, 3, GL_FLOAT, false, 32, 12);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(2, 2, GL_FLOAT, false, 32, 24);
		glEnableVertexAttribArray(2);
		
		imgLoader = new ImageLoader("res/SSBU-Final_Destination.jpg");
		texLoader = new TextureLoader(imgLoader);
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
		// glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		texLoader.bind();
		shaderProgram.bind();
		
		glBindVertexArray(vao);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glEnableVertexAttribArray(2);
		
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
		glDrawElements(GL_TRIANGLES, 6, GL_UNSIGNED_INT, 0);
		
		glDisableVertexAttribArray(0);
		glDisableVertexAttribArray(1);
		glDisableVertexAttribArray(2);
		glBindVertexArray(0);
		
		ShaderProgram.unbind();
	}
}
