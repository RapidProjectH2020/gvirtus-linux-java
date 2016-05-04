# How To install GVirtuS framework and plugins#
## Prerequisites: ##
GCC, G++

OS: Ubuntu 10.04 (tested) / 12.04 (tested) / 14.04 (tested) 

CUDA Toolkit: Version 6.5

## Installation: ##
1) Clone the GVirtuS main repository

    git clone https://github.com/RapidProjectH2020/GVirtuS.git

In the directory “GVirtuS” there are three directories named “gvirtus”, “gvirtus.cudart” and "gvirtus.cudadr".

“gvirtus” contains the framework.

“gvirtus.cudart” and "gvirtus.cudadr" contains the cuda runtime plugin and the cuda driver plugin.


2) Launch the installer script indicating the destination folder of the installation (es. "/home/rapid/opt"):

    ./gvirtus-installer "GVIRTUS_PATH"

To check your installation please check the following directories:

Check GVIRTUS_PATH/lib for frontend and backend directories


## EXAMPLE cuda application ##

### Backend machine (physical GPU and Cuda required) ###

On the remote machine where the cuda executables will be executed

Modify the Gvirtus configuration file backend if the default port 9991 is occuped or the machine is remote:

GVIRTUS_PATH/etc/gvirtus.properties

    #
    # gVirtuS config file
    #
    
    #
    # Communicator
    #   AfUnix: afunix://path:mode
    #   Shm: shm://
    #   Tcp: tcp://hostname:port
    #   VMShm: vmshm://hostname:port
    
    communicator : tcp://localhost:9991 #change localhost with remote host if necessary
    plugins : cudart, cudadr
    
    #
    # End Of File
    #


Export the dynamic CUDA library:(typically /usr/local/cuda/lib64)

    export LD_LIBRARY_PATH=”<CUDA_PATH>/lib64” 

Execute application server gvirtus-backend with follow command:

    GVIRTUS_PATH/bin/gvirtus-backend

### Frontend machine (No GPU or Cuda required) ###

The default port and ip are setted in the GvirtuSFE.java file:
    
    static String ip="127.0.0.1"; //change localhost with remote host if necessary
    static int port = 9991;
    
Change this value for other configurations.

Run the Java project!

###P.S. Run the project only AFTER have launched the gvirtus-backend process on the remote machine!###

