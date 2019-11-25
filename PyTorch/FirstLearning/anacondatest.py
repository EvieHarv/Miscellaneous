from __future__ import print_function
import torch

x = torch.zeros(5, 3, dtype=torch.long)
#print(x)

x = x.new_ones(5, 3, dtype=torch.double)
#print(x)

x = torch.rand_like(x, dtype=torch.float)
print(x)

#print(x.size())

y = torch.rand(5, 3)

#print(x + y)
# or
#print(torch.add(x, y))

result = torch.empty(5, 3)
torch.add(x, y, out=result)
#print(result)

# or for destructive: (according to docs, anything suffixed with _ is destructive)

y.add_(x)
print(y)

#print(x[:, 1]) # always loved indexing like this, coming from C#

x = torch.randn(4, 4)
y = x.view(4*4)
z = x.view(-1, 8) # so -1 infers dims? appears that way 
print(x.size(), y.size(), z.size()) 

a = torch.randn(1)
print(a)
print(a.item())

a = torch.ones(5)
b = a.numpy() # Links object
print(b)
a.add_(1)
print(a)
print(b)

import numpy as np 

a = np.ones(5)
b = torch.from_numpy(a)
np.add(a, 1, out=a)
print(a)
print(b)

x = torch.randn(1)

if torch.cuda.is_available(): # i know it is but docs put it there so fine
    device = torch.device('cuda') # i hate syntax like this but im gonna have to deal with it.
    y = torch.ones_like(x, device=device) # Create 1's initalized tensor yeeting its values into CUDA processing
    x = x.to(device)
    z = x + y
    print(z)
    print(z.to("cpu", torch.double)) # oh huh. thats neat