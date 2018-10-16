#function definition
def collatz(number):
	newNumber = 0
	if number % 2 == 0:
		newNumber = number//2
		#print('Return value is ' + str(newNumber))
		print(newNumber)

	else:
		newNumber = 3 * number + 1
		#print ('Return value is ' + str(newNumber))
		print(newNumber)

	return newNumber

#Program start
print('Enter number')

try: 
	myNum = int(input())

	while myNum != 1:
		myNum = collatz(myNum)

except ValueError:
	print('Number must be an integer')


