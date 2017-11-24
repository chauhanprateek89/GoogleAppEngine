// JDO class that represents a GPU name and it's six features
package pc;

//imports
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;


@PersistenceCapable
public class Drivers {
	//using GPU_name as primary key, so that only one record per 
	//GPU name can exist in the database.
	@PrimaryKey
	@Persistent
	private String GPU_name;	
	
	//six features of the GPU defined
	@Persistent
	private String geometryShader;
	
	@Persistent
	private String tesselationShader;
	
	@Persistent
	private String shaderInt16;
	
	@Persistent
	private String sparseBinding;
	
	@Persistent
	private String textureCompressionETC2;
	
	@Persistent
	private String vertexPipelineStoresAndAtomics;

	//getter and setter methods for every features of the GPU
	public String getGPU_name() {
		return GPU_name;
	}
	public void setGPU_name(final String gPU_name) {
		GPU_name = gPU_name;
	}
	public String getGeometryShader() {
		return geometryShader;
	}
	public void setGeometryShader(final String geometryShader) {
		this.geometryShader = geometryShader;
	}
	public String getTesselationShader() {
		return tesselationShader;
	}
	public void setTesselationShader(final String tesselationShader) {
		this.tesselationShader = tesselationShader;
	}
	public String getShaderInt16() {
		return shaderInt16;
	}
	public void setShaderInt16(final String shaderInt16) {
		this.shaderInt16 = shaderInt16;
	}
	public String getSparseBinding() {
		return sparseBinding;
	}
	public void setSparseBinding(final String sparseBinding) {
		this.sparseBinding = sparseBinding;
	}
	public String getTextureCompressionETC2() {
		return textureCompressionETC2;
	}
	public void setTextureCompressionETC2(final String textureCompressionETC2) {
		this.textureCompressionETC2 = textureCompressionETC2;
	}
	public String getVertexPipelineStoresAndAtomics() {
		return vertexPipelineStoresAndAtomics;
	}
	public void setVertexPipelineStoresAndAtomics(
			final String vertexPipelineStoresAndAtomics) {
		this.vertexPipelineStoresAndAtomics = vertexPipelineStoresAndAtomics;
	}
		
}
