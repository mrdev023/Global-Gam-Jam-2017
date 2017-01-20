package globalgamejam.math;


import java.nio.*;
import java.util.*;

import org.lwjgl.*;

/**
 * Class created by MrDev023 (Florian RICHER) on 14/01/2017
 */
public class Matrix4f {

	public float[][] m = null;
	
	public Matrix4f(){
		m = new float[][]{
			{1,0,0,0},
			{0,1,0,0},
			{0,0,1,0},
			{0,0,0,1}
		};
	}
	
	public Matrix4f(float[][] m){
		this.m = m;
	}
	
	public Matrix4f loadIdentity(){
		m = new float[][]{
			{1,0,0,0},
			{0,1,0,0},
			{0,0,1,0},
			{0,0,0,1}
		};
		return this;
	}
	
	public Matrix4f rotate(Quaternion q){
		Matrix4f rot = q.toMatrixRotation();
		m = mul(rot).getM();
		return this;
	}

	
	public void rotate(float x,float y,float z){
		x = Mathf.toRadians(x);
		y = Mathf.toRadians(y);
		z = Mathf.toRadians(z);
		Matrix4f rx = new Matrix4f(new float[][]{
			{1,0,0,0},
			{0,Mathf.cos(x),-Mathf.sin(x),0},
			{0,Mathf.sin(x),Mathf.cos(x),0},
			{0,0,0,1}
		});
		
		Matrix4f ry = new Matrix4f(new float[][]{
			{Mathf.cos(y),0,Mathf.sin(y),0},
			{0,1,0,0},
			{-Mathf.sin(y),0,Mathf.cos(y),0},
			{0,0,0,1}
		});
		
		Matrix4f rz = new Matrix4f(new float[][]{
			{Mathf.cos(z),-Mathf.sin(z),0,0},
			{Mathf.sin(z),Mathf.cos(z),0,0},
			{0,0,1,0},
			{0,0,0,1}
		});
		Matrix4f m1 = (rz.mul(ry.mul(rx)));
		m = mul(m1).getM();
	}
	
	public static Matrix4f rotate(Vector3f forward, Vector3f up, Vector3f right)
	{
		Matrix4f mat = new Matrix4f(new float[][]{
			{right.getX(),	right.getY(),	right.getZ()	,0},
			{up.getX(),		up.getY(),		up.getZ()		,0},
			{forward.getX(),forward.getY(),	forward.getZ()	,0},
			{0,0,0,1}
		});
		return mat;
	}
	
	public Matrix4f translate(float x,float y,float z){
		Matrix4f mat = new Matrix4f(new float[][]{
			{1,0,0,x},
			{0,1,0,y},
			{0,0,1,z},
			{0,0,0,1}
		});
		m = mul(mat).getM();
		return this;
	}
	
	public Matrix4f scale(float x,float y,float z){
		Matrix4f mat = new Matrix4f(new float[][]{
			{x,0,0,0},
			{0,y,0,0},
			{0,0,z,0},
			{0,0,0,1}
		});
		m = mul(mat).getM();
		return this;
	}

	public Matrix4f mul(Matrix4f mat){
		Matrix4f ma = new Matrix4f();
		for(int i = 0;i < 4;i++){
			for(int j = 0;j < 4;j++){
				ma.m[i][j] = m[i][0] * mat.m[0][j] + 
						m[i][1] * mat.m[1][j] + 
						m[i][2] * mat.m[2][j] + 
						m[i][3] * mat.m[3][j];
			}
		}
		return ma;
	}
	
	public Matrix4f Ortho2D(float left, float right, float bottom, float top, float near, float far)
	{
		float width = right - left;
		float height = top - bottom;
		float depth = far - near;
		
		m = new float[][]{
			{2/width,0,0,-(right + left)/width},
			{0,2/height,0,-(top + bottom)/height},
			{0,0,-2/depth,-(far + near)/depth},
			{0,0,0,1}
		};

		return this;
	}
	
	public Matrix4f perspective(float fov, float aspectRatio, float zNear, float zFar)
	{
		float f = fov;
		fov = Mathf.toRadians(f);
		float tanHalfFOV = (float)Math.tan(fov / 2);
		float zRange = zNear - zFar;
		
		m = new float[][]{
			{1.0f / (tanHalfFOV * aspectRatio),0,0,0},
			{0,1.0f / tanHalfFOV,0,0},
			{0,0,(-zNear -zFar)/zRange,2.0f * zFar * zNear / zRange},
			{0,0,1,0}
		};
		
		return this;
	}
	
	public FloatBuffer getBuffer(){
		FloatBuffer buffer = BufferUtils.createFloatBuffer(4 * 4);
		for(int i = 0;i < 4;i++){
			buffer.put(m[i]);
		}
		buffer.flip();
		return buffer;		
	}

	public String toString(){
		int size = 3;
		int max = 10;
		StringJoiner st = new StringJoiner("\n","--------Mat4-Begin--------\n","\n--------Mat4-End----------");
		for(int i = 0;i < 4;i++){
			StringJoiner st2 = new StringJoiner(" | ");
			for(int j = 0;j < 4;j++){
				String value = Mathf.cut(m[i][j], size) + "";
				for(int k = value.length();k < max;k++){
					value += " ";
				}
				st2.add(value);
			}
			st.add(st2.toString());
		}
		return st.toString();
	}
	
	public float[][] getM() {
		return m;
	}

	public void setM(float[][] m) {
		this.m = m;
	}
	
	public Matrix4f copy(){
		return new Matrix4f(this.getM());
	}
	
}
