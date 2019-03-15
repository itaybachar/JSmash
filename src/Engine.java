
public class Engine implements Runnable {
	
	private long handle;		// Probably not needed
	Thread tEngine;
	
	private boolean stop = false;
	
	public Stage stage;
	
	public Engine(long handle)
	{
		// window handle
		this.handle = handle;
	}
	
	
	// At this point all GLFW init is complete
	public void init()
	{
		tEngine = new Thread(this);
		stage = new Stage();
		tEngine.start();
	}
	
	public void renderAll()
	{
		stage.render();
	}
	
	public void kill()
	{
		stop = true;
	}
	
	// Main logic loop
	public void run()
	{
		while (stop) {
			try {
				Thread.sleep(10);
			} catch (Exception e) {
			}			
			//stage.render();
		}
	}
}
