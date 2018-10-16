#include "path.h"
#include <queue>
#include <stack>
using namespace std;

path::path(const PNG & im, pair<int,int> s, pair<int,int> e) {
	start = s;
	end = e;
	image = im;
	BFS();
}

void path::BFS(){
	vector<vector<bool>> V(image.height(), vector<bool> (image.width(),false));
	vector<vector<pair<int,int>>> P(image.height(), vector<pair<int,int>> (image.width(),end));
	queue<pair<int,int>> locationQ;

	V[start.second][start.first] = true;
	P[start.second][start.first] = start;
	locationQ.push(start);

	/* your code here */

    while (!locationQ.empty()) {

    	pair<int,int> curr = locationQ.front();
		vector<pair<int,int>> n = neighbors(curr);
        locationQ.pop();
        for (unsigned long i = 0; i < n.size(); ++i) {
        	pair<int,int> sidebuddy = n[i];
        	if (good(V, curr, sidebuddy)) {
            	V[sidebuddy.second][sidebuddy.first] = true;
				P[sidebuddy.second][sidebuddy.first] = curr;

				// printf("%d, %d\n", curr.first, curr.second);
				// pair<int,int> pred = P[sidebuddy.second][sidebuddy.first];
				// printf("%d, %d\n", pred.first, pred.second);

            	locationQ.push(sidebuddy);

        	}
    	}
	}

	pathPts = assemble(P,start,end);
}

PNG path::render(){
	/* your code here */		
	PNG shortestPath(image);
	RGBAPixel red = RGBAPixel(255, 0, 0);
	for (unsigned long i = 0; i < pathPts.size(); i++) {
		RGBAPixel * pix = shortestPath.getPixel(pathPts[i].first, pathPts[i].second);
		*pix = red;
	}

	return shortestPath;
}

bool path::good(vector<vector<bool>> & v, pair<int,int> curr, pair<int,int> next){
    /* your code here */

    if (next.first < 0 || next.first >= (int)image.width() || next.second < 0 || next.second >= (int)image.height()) {
    	return false;
    } else if (v[next.second][next.first]) {
    	return false;
    } else if(!closeEnough(*image.getPixel(curr.first, curr.second), *image.getPixel(next.first, next.second))) {
    	return false;
    } else {
    	return true;
    }
}

vector<pair<int,int>> path::neighbors(pair<int,int> curr) {
	vector<pair<int,int>> n;
	
    /* your code here */
    pair<int, int> currTop;
    pair<int, int> currRight;
    pair<int, int> currBtm;
    pair<int, int> currLeft;

    currLeft = make_pair(curr.first - 1, curr.second);
    currBtm = make_pair(curr.first, curr.second + 1);
    currRight = make_pair(curr.first + 1, curr.second);
	currTop = make_pair(curr.first, curr.second - 1);
    
    
    
	n.push_back(currLeft);
	n.push_back(currBtm);
	n.push_back(currRight);
    n.push_back(currTop);
    
    
    

    return n;
}

vector<pair<int,int>> path::assemble(vector<vector<pair<int,int>>> & p,pair<int,int> s, pair<int,int> e) {
	vector<pair<int,int>> allPts;
	stack<pair<int,int>> resultStack;
	pair<int,int> curr = e;
	pair<int, int> pred;

	/* your code here */

	while(curr != s) {
		// check if there is a path between s and e
		if (p[e.second][e.first] == end){
			break;
		} else {
			resultStack.push(curr);
			pred = curr;
			curr = p[curr.second][curr.first];
		}
		
	}
	resultStack.push(s);
	while(!resultStack.empty()) {
		allPts.push_back(resultStack.top());
		resultStack.pop();
	}
	return allPts;
}


/* PA4 - Completed for you for 2018S Semester */

bool path::closeEnough(RGBAPixel p1, RGBAPixel p2){
	int dist = (p1.r-p2.r)*(p1.r-p2.r);
	dist    += (p1.g-p2.g)*(p1.g-p2.g);
	dist    += (p1.b-p2.b)*(p1.b-p2.b);
	return (dist <= 80);
}

vector<pair<int,int>> path::getPath() { 
	return pathPts;
}

int path::length() { 
	return pathPts.size();
}
