import network
import numpy as np
from PIL import Image

with np.load('trained_network.npz') as data:
    biases = data['biases']
    weights = data['weights']

net = network.Network([784, 30, 10], biases = biases, weights = weights)

img = Image.open("imgs/61.png").convert("L")

arr = np.array(img).ravel()

data = [[x/256] for x in arr]

print (data)

print(np.argmax(net.feedforward(data)))