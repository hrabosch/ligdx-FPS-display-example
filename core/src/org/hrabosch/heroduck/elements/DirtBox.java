package org.hrabosch.heroduck.elements;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;

public class DirtBox {
    private Texture sideTexture = new Texture(Gdx.files.internal("isometric/dirt_texture.png"));
    private Texture topTexture = new Texture(Gdx.files.internal("isometric/map_tile_grass.jpg"));
    public Model getModel() {
        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder sidesBuilder = modelBuilder.part("sides", GL20.GL_TRIANGLES,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates,
                new Material(TextureAttribute.createDiffuse(sideTexture)));

        float size = 1.0f;
        sidesBuilder.rect(
                -size / 2, -size / 2, size / 2,   // bottom-left
                size / 2, -size / 2, size / 2,   // bottom-right
                size / 2,  size / 2, size / 2,   // top-right
                -size / 2,  size / 2, size / 2,   // top-left
                0, 0, 1);  // front face normal

        sidesBuilder.rect(
                size / 2, -size / 2, -size / 2,  // bottom-right
                -size / 2, -size / 2, -size / 2,  // bottom-left
                -size / 2,  size / 2, -size / 2,  // top-left
                size / 2,  size / 2, -size / 2,  // top-right
                0, 0, -1);  // back face normal

        sidesBuilder.rect(
                -size / 2, -size / 2, -size / 2,  // bottom-left
                -size / 2, -size / 2,  size / 2,  // bottom-right
                -size / 2,  size / 2,  size / 2,  // top-right
                -size / 2,  size / 2, -size / 2,  // top-left
                -1, 0, 0);  // left face normal

        sidesBuilder.rect(
                size / 2, -size / 2,  size / 2,  // bottom-left
                size / 2, -size / 2, -size / 2,  // bottom-right
                size / 2,  size / 2, -size / 2,  // top-right
                size / 2,  size / 2,  size / 2,  // top-left
                1, 0, 0);  // right face normal

        sidesBuilder.rect(
                -size / 2, -size / 2, -size / 2,  // bottom-left
                size / 2, -size / 2, -size / 2,  // bottom-right
                size / 2, -size / 2,  size / 2,  // top-right
                -size / 2, -size / 2,  size / 2,  // top-left
                0, -1, 0);  // bottom face normal

        MeshPartBuilder topBuilder = modelBuilder.part("top", GL20.GL_TRIANGLES,
                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates,
                new Material(TextureAttribute.createDiffuse(topTexture)));
        topBuilder.rect(
                -size / 2,  size / 2,  size / 2,  // bottom-left
                size / 2,  size / 2,  size / 2,  // bottom-right
                size / 2,  size / 2, -size / 2,  // top-right
                -size / 2,  size / 2, -size / 2,  // top-left
                0, 1, 0);  // top face normal

        return modelBuilder.end();
    }
}