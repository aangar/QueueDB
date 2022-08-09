# Project: Data Structures, personal learing repo.
### Author: **AJ Angarita**
### Project Description: A repo where I put files to work on my understanding on data structures.
### Motive: Wanted to start somewhere with data structures before formal instruction.

---
Binary Tree Progress Map:
- [x] Tree generation up to a specified value without exceeding.
- [x] Finding and Generating the route for a value that might not have a node.
- [x] Make an interpreter for ease-of-access to find in a tree. EX: Find the path to 100 in a Search Tree.  
- [x] Determining if a child node exists in a structured tree.
- [x] Validation to ensure automatic generation is working properly. 
- [x] Preorder Traversal for a Binary Tree.
- [ ] User Incorporation.
    - [x] User specified BST Generation Boundaries.
    - [x] User specified search value on BST, no generation required.
    - [x] User specified search if a value exists on a tree.
        - [x] Indicate the value exists.
        - [x] Indicate the value does not exist
    - [ ] Allow a more user-generated and interactive experience.
- [ ] Making a tree based off an array / list of values. Traversal.
    - [ ] Generate a tree from an array of values. *Needs research*
    - [ ] Find a value from the previously generated tree.
- [ ] insert a root at a value and generate the tree from there. Ex: Get the node for a value of 58, and start a new tree starting from there with a value of 5.
- [ ] Package different Trees as classes, and their methods. If there is overlap like traversals, or verifying existance, keep it in a shared methods file.
---
QueueDB Progress Map:
- [x] Making Documents from a Queue.
- [x] Temporary Database. Will remove older data on new data generation.
- [x] Making a base "DatabaseDocument.java" interface containing the ID, reduces duplication.
    - [x] Create Class.
    - [x] Create id field and method.
    - [x] Convert existing classes.
    - [ ] **ONGOING** Implement this with newer classes.
- [ ] Finding a Better Way to Generate DB Documents.
    - [ ] Converter Class: will apply to all documents.
- [ ] Allow a user to generate documents.
    - [x] From a List of Names (Current Setup).
    - [ ] Allow a user to load a JSON and parse it from there.
- [ ] Searching this Database.
    - [ ] Finding a match based on a query.
        - [ ] String matching.
        - [ ] GT / LT / EQ / NE, etc...
        - [ ] Multiple Queries.
    - [ ] Finding all.
    - [ ] Returning found documents.
        - [ ] How to convert from JSON to a Object / Class.
            - [ ] ObjectMapper from Jackson?
- [ ] Efficiency.
    - [ ] Research Efficient methods and practices to load data.
    - [ ] Find best ways to secure it and use small amounts of space.
    - [ ] Use little amounts of resources.
- [ ] DB AppFlow.
    - [ ] Initialization
        - [x] Windows
        - [ ] MacOS
        - [ ] Linux
    - [ ] Termination
        - [ ] Flag / User input?
        - [ ] Raw kill
            - [ ] Verify File Integrity on startup task.
---
 
What comes next?
1. Constant Tasks
    - Constant general cleanup of unused methods.
    - Searching / Generation / Algorithm Refinement.
2. Defined Tasks: Related to Binary Trees
    - Searching by Non-Nested Data Type
    - Searching by Nested Data Type ( Arrays, Lists, HashMaps, etc... )
    - Searching by field in Class, potentially.
3. Other Things
    - Other data structure work can go here.
    - Make files for best practices, development ideas.

---

Topics to Research

1. Self Balancing Tree.
2. Balanced Tree.
3. DFS / BFS.
4. Insertion Methods.
5. Removal Methods.
6. Traversal Types and applications.
    - Inorder [Left, Root, Right]
    - Postorder [Left, Right, Root]
    - Preorder [Root, Left, Right]

---

Thanks for reading. Not sure how often this will update, but I can assume it will.
<br/>
Eventually.
