from __future__ import print_function
import torch

x = torch.zeros(5, 3, dtype=torch.long)
print(x)

x = x.new_ones(5, 3, dtype=torch.double)
print(x)

x = torch.rand_like(x, dtype=torch.float)
print(x)

print(x.size())

y = torch.rand(5, 3)

print(x + y)
#or
print(torch.add(x, y))