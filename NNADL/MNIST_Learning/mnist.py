import network
import numpy as np

with np.load('mnist.npz') as data:
    trainingData = data['training_images']
    testData = data['training_labels']

net = network.Network([784, 30, 10])

dataTrain = list(zip(trainingData[0:45000 - 1], testData[0:45000 - 1]))
dataTest = list(zip(trainingData[45000 - 1:-1], testData[45000 - 1:-1]))

print(dataTrain[0])

#net.SGD(dataTrain, 30, 10, 2.5, testData=dataTest)

#np.savez("trained_network", biases = net.biases, weights = net.weights)