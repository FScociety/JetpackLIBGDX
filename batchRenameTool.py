import os

print("Path:")
filePath = input()

i = 0;
name = ""
lastname = ""

for filename in os.listdir(filePath):
    name = os.path.splitext(filename)[0]
    name = ''.join(i for i in name if not i.isdigit())

    print("testing name: " + name + " " + lastname)

    if (name == lastname):
        i+=1
    else:
        i = 0

    name = ''.join(i for i in name if not i.isdigit())
    os.rename(filePath + "/" + filename, filePath + "/" + name + str(i) + ".png")

    print("Saving to: " + name + str(i) + ".png")

    lastname = name
