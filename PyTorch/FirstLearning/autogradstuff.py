import torch

x = torch.ones(2, 2, requires_grad=True)

y = x + 2
#print(y)
#print(y.grad_fn)

z = y * y * 3
out = z.mean()

#print(z, out)
#
#a = torch.randn(2, 2)
#a = ((a * 3) / (a - 1))
##print(a.requires_grad)
#a.requires_grad_(True)
##print(a.requires_grad)
#b = (a * a).sum()
##print (b.grad_fn)

print(out)
out.backward()
print(x)
print(x.grad) # the orig. derivitive of d(o)/dx where o(x) = (1/4) ∑(i) z_i, z_i=3(x_i+2)^2

h = torch.ones(2, 2, requires_grad=True) 

i = h - 3
j = i * i * 8

k = j.mean()
"""
Alright, so if im right, it should be 
k(h) = (1/4) ∑(i) 8(h_n - 3)^2
which should differentiate to
d(k)/d(h) = 4(h_n - 3)
so for all h_n = 1, our final tensor output should be -8 in all slots?
"""

k.backward()
print(h)
print(h.grad)

"""
WOOOOOO I WAS RIGHT!
i know its not that hard and its only basic calculus but for some reason that .backward() algorithm took me an hour or two to wrap my head around.
I'm really happy that that test worked the first time, that gives me confidence that I actually know what im doing lmao
"""

#x = torch.randn(3, requires_grad=True)
#
#y = x * 2
#while y.data.norm() < 1000:
#    y = y * 2
#print(y)
#
#v = torch.tensor([0.1, 1.0, 0.0001], dtype=torch.float)
#y.backward(v)

#print(x.grad)
#
#print(x.requires_grad)
#print((x ** 2).requires_grad)
#
#with torch.no_grad():
#    print((x ** 2).r equires_grad)