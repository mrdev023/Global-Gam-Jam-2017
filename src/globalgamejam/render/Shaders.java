package globalgamejam.render;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import globalgamejam.input.*;
import globalgamejam.math.*;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Shaders {
	
	public int program;

	public static Shaders MAIN_SHADERS;

	static{
		try{
			MAIN_SHADERS = new Shaders("res/shaders/main.vert","res/shaders/main.frag");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public Shaders(String vertexFile,String fragmentFile) throws Exception{
		String fragmentShader = IO.loadFile(fragmentFile);
		String vertexShader = IO.loadFile(vertexFile);
		
		if(program  != -1)glDeleteProgram(program);
		program = glCreateProgram();
		int vert = glCreateShader(GL_VERTEX_SHADER);
		int frag = glCreateShader(GL_FRAGMENT_SHADER);
		glShaderSource(vert, vertexShader);
		glShaderSource(frag, fragmentShader);
		glCompileShader(vert);
		if (glGetShaderi(vert, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(vert, 2048));
			System.exit(1);
		}else{
			System.out.println("Vertex compiled !");
		}
		glCompileShader(frag);
		if (glGetShaderi(frag, GL_COMPILE_STATUS) == GL_FALSE) {
			System.err.println(glGetShaderInfoLog(frag, 2048));
			System.exit(1);
		}else{
			System.out.println("Fragment compiled !");
		}
		glAttachShader(program, vert);
		glAttachShader(program, frag);
		glLinkProgram(program);
		glValidateProgram(program);
		glDeleteShader(frag);
		glDeleteShader(vert);
	}
	
	public void bind(){
		glUseProgram(program);
	}
	
	public void unbind(){
		glUseProgram(0);
	}
	
	public int getAttribLocation(String name){
		return glGetAttribLocation(program, name);
	}
	
	public void destroy(){
		if(program == 0)return;
		if(glIsProgram(program))unbind();
		glDeleteProgram(program);
	}
	
	public void uniform(String name,float v){
		glUniform1f(glGetUniformLocation(program, name), v);
	}
	
	public void uniform(String name,Vector3f vec){
		glUniform3f(glGetUniformLocation(program, name), vec.x,vec.y,vec.z);
	}
	
	public void uniform(String name,Vector4f vec){
		glUniform4f(glGetUniformLocation(program, name), vec.x,vec.y,vec.z,vec.w);
	}
	
	public void uniform(String name,Matrix4f mat){
		glUniformMatrix4fv(glGetUniformLocation(program, name),true, mat.getBuffer());
	}

	public void uniform(String name, Color4f v) {
		glUniform4f(glGetUniformLocation(program, name), v.getR(),v.getG(),v.getB(),v.getA());
	}
	
	public void uniform(String name,int v){
		glUniform1i(glGetUniformLocation(program,name), v);
	}
	
}
