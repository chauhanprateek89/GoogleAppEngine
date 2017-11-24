# Building a GPU features database

This application builds a simple database about GPUs and the features they support.
The application is capable of 2 things:
1. able to add in the name of a GPU along with the features that it supports.
2. able to choose a set of supporting features and figure out what GPUs support them.

The GPU database generated emulates a small subset of the information found on the Vulkan GPU database.

Six features of a GPU are mapped:
- geometryShader
- tesselationShader
- shaderInt16
- sparseBinding
- textureCompressionETC2
- vertexPipelineStoresAndAtomics

Each GPU has its key as the name otself along with the information for the above mentioned six properties.

When a GPU is added, a user is able to click a series of checkboxes to enable or disable features.
When an add button is clicked duplicate or pre-existing names of the GPU in the database cannot be added.

For queries, a user can choose a combination of features and run a query to find the set of GPU names that support all the required features.
