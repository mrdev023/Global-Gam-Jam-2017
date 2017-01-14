package globalgamejam.render;

import globalgamejam.math.Color4f;
import globalgamejam.math.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.*;

import java.nio.FloatBuffer;

/**
 * Created by MrDev023 (Florian RICHER) on 14/01/2017.
 */
public abstract class Tile {

    private int vbo;
    private Texture texture;
    private Color4f color;
    private Matrix4f transform;
    private int size;

    public Tile(){
        this.texture = new Texture(0,0,0);
        this.transform = new Matrix4f();
        this.color = Color4f.WHITE;
        this.vbo = GL15.glGenBuffers();
        load();
    }

    private void load(){
        float[] a = new float[]{
                -1.0f,-1.0f,        0.0f,0.0f,
                1.0f,-1.0f,         1.0f,0.0f,
                1.0f,1.0f,          1.0f,1.0f,
                1.0f,-1.0f,         0.0f,1.0f,
        };
        FloatBuffer buffer = BufferUtils.createFloatBuffer(a.length);
        buffer.put(a).flip();
        size = a.length/(2+2);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);

        GL20.glEnableVertexAttribArray(Shaders.MAIN_SHADERS.getAttribLocation("vert"));
        GL20.glVertexAttribPointer(Shaders.MAIN_SHADERS.getAttribLocation("vert"), 2, GL11.GL_FLOAT, false, (2+2)*4, 0);

        GL20.glEnableVertexAttribArray(Shaders.MAIN_SHADERS.getAttribLocation("vertTexCoord"));
        GL20.glVertexAttribPointer(Shaders.MAIN_SHADERS.getAttribLocation("vertTexCoord"), 2, GL11.GL_FLOAT, true, (2+2)*4, 2*4);

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
    }

    public void render(){
        Shaders.MAIN_SHADERS.bind();
        Shaders.MAIN_SHADERS.uniform("camera", Camera.matrix);
        Shaders.MAIN_SHADERS.uniform("transform", transform);
        Shaders.MAIN_SHADERS.uniform("projection", DisplayManager.projection);
        Shaders.MAIN_SHADERS.uniform("color", this.color);

        GL13.glActiveTexture(GL13.GL_TEXTURE0);
        texture.bind();

        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vbo);
        GL11.glDrawArrays(GL11.GL_QUADS, 0, size);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
        texture.unbind();
        Shaders.MAIN_SHADERS.unbind();
    }

    public void destroy(){
        GL15.glDeleteBuffers(vbo);
        texture.destroy();
        transform = null;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Color4f getColor() {
        return color;
    }

    public void setColor(Color4f color4f) {
        this.color = color4f;
    }

    public int getVbo() {
        return vbo;
    }

    public Matrix4f getTransform() {
        return transform;
    }
}
