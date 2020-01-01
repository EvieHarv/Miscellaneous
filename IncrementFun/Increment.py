import os
import sys

def increment(i):
    f = open("IncrementNumber.py", "w+")
    lines = []
    lines.append("i = %s\n" % (i))
    lines.append("def increment(i):\n")
    for x in range(i+1):
        lines.append(" if i == %s:\n" % (x))
        lines.append("  return %s\n" % (x+1))
    lines.append("i = increment(i)\n")
    lines.append("f = open(\"incrementedNumber.txt\", \"w+\")\n")
    lines.append("f.write(str(i))\n")
    lines.append("f.close()")
    f.writelines(lines)
    f.close()
    os.system(sys.executable + " IncrementNumber.py")
    while (os.path.exists("incrementedNumber.txt") == False):
        pass
    f = open("incrementedNumber.txt", "r")
    i = f.read()
    i = int(i)
    return i

print(increment(100000))