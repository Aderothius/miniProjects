# inventory.py
import pprint

def displayInventory(inventory):
	print('Inventory:')
	itemTotal = 0

	for k, v in inventory.items():

		print(str(inventory[k]) + ' ' + k)
		itemTotal = itemTotal + inventory[k]


	pprint.pprint('Total number of items: ' + str(itemTotal))


stuff = {'rope': 1, 'torch': 6, 'gold coin': 42, 'dagger': 1, 'arrow': 12}
displayInventory(stuff)


dragonLoot = ['gold coin', 'dagger', 'gold coin', 'gold coin', 'ruby']

def addToInventory(inventory, addedItems):
	for i in range(len(dragonLoot)):
		if dragonLoot[i] in inventory.keys():
			inventory[dragonLoot[i]] = inventory[dragonLoot[i]] + 1

		else:
			inventory.setdefault(dragonLoot[i], 1)	
		
	return inventory

stuff = addToInventory(stuff, dragonLoot)
displayInventory(stuff)