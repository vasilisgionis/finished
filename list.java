class Node {
	public Node next;
	public Node prev;
	private int key;

	public Node(int key) {
		this.key = key;
	}

	public int key() {
		return this.key;
	}

	public void print(String pre, String post) {
		System.out.print(pre + this.key + post);
	}
}

class List {
	private Node head;
	private Node tail;

	public List() {
		head = new Node(0);
		tail = new Node(0);
		head.next = tail;
		tail.prev = head;
	}

	public void insertFirst(int key) {
		Node tmp = new Node(key);

		tmp.prev = head;
		tmp.next = head.next;

		head.next = tmp;
		tmp.next.prev = tmp;
	}

	public List copy() {
		List list = new List();
		Node node = this.tail.prev;
		while(node != this.head) {
			list.insertFirst(node.key());
			node = node.prev;
		}
		return list;
	}

	public void print() {
		Node current = this.head.next;
		System.out.print("[");
		while(current != this.tail) {
		       	current.print(" ", ",");
			current = current.next;
		}
		System.out.println(" ]");
	}

	public List filter(List list) { //η σκεψη πισω απο την υλοποιηση της filter ειναι η συνεχομενη συγκριση των keys των 2 λιστων και η εκγχωρηση τους σε μια νεα λιστα με ονομα filterlist 
		List filterlist = new List(); //δημιουργω την λιστα filterlist 
		Node thisnode=this.head.next; //και δημιουργω τον node ο οποιος παιρνει την τιμη του this.next.head και μεταγενεστερα τις επομενες τιμες των επομενων nodes της λιστας this  
		while (thisnode!=this.tail){ //ξεκιναω μια λουπα οπου εχω ως condition το thisnode node να μην ειναι κενο (πραγμα το οποιο πραγματοποιειται οταν φτασει το node μετα το tail) 
			Node listnode=list.head.next; // δημιουργω εναν επιπλεον node listnode ο οποιος θα παιρνει τις τιμες των επομενων nodes της λιστας list
			while (listnode!=list.tail) { //ξεκιναω την εμφολευμενη λουπα η οποια εχει ως συνθηκη η listnode να μην ειναι κενη 
				if (thisnode.key()==listnode.key()) { //συγκρινουμε τωρα τα κλειδια των 2 nodes και αν ειναι ιδια τοτε :
					filterlist.insertFirst(thisnode.key()); //προσθετουμε το κλειδι της thisnode στην λιστα filterlist
				}

				listnode = listnode.next; //επειτα παμε στον επομενο κομβο της list
			
			}
			thisnode = thisnode.next; //και αφου συγκριθουν ολα τα στοιχεια της λιστας list, παμε στον επομενο κοβμο της this 
		}
		return filterlist; //επιστρεφουμε την λιστα filterlist
	}

	public List mergeWith(List list) { // η σκεψη πισω απο την υλοποιηση της mergeWith ειναι η αντιγραφη των 2 λιστων, να γινουν οι 2 λιστες insertion sorted και να τοποθετηθουν τα στοιχεια τους με συγκεκριμενο τροπο στην mergedlist, 
		// ο τροπος ειναι οτι σε μια loop θα συγκρινονται τα key nodes μεταξυ τους και ο μεγαλυτερος node θα μπαινει το κλειδι του στην λιστα και επειτα θα διαγραφεται μεχρι να βρεθει ο επομενος μεγαλυτερος απο αυτον etc
		List copiedthis, copiedlist = new List();
		copiedthis=this.copy(); copiedlist=list.copy(); //αντιγραφω τις 2 λιστες για να συγκρινω τα nodes τους και αν τα βαλω σε αυξουσα σειρα σε μια νεα λιστα με ονομα mergedlist
		insertionSort(copiedlist); //επειτα χρησιμοποιω την insertionSort που υλοποιησα ως μεθοδο για να ταξινομησω τα αντιγραφα των λιστων σε αυξουσα σειρα για να κανω την συγκριση πιο ευκολη 
		insertionSort(copiedthis);

		List mergedlist = new List(); //φτιαχνουμε την mergedlist 
		Node current = copiedthis.tail.prev; //και φτιαχνουμε 2 κομβους, 1 για καθες απο τις λιστες και τους αρχικοποιουμε με τις τιμες tail.prev
		Node currentlist = copiedlist.tail.prev;
		// το concept ειναι οτι θα κοιταμε απο το τελος της 2 λιστες και θα βαζουμε τα μεγαλυτερα keys (εφοσον οι λιστες ειναι sorted τοτε τα μεγαλυτερα keys ειναι στο τελος των λιστων)
		// στην λιστα οποτε με καθε περασμα απο την παρακατω loop θα μπαινουν ολα τα στοιχεια με αυξουσα σειρα καθως ο κομβος που μπαινει στην mergedlist επειτα διαγραφεται 
		while (current != copiedthis.head && currentlist!=copiedlist.head){ //ξεκιναω μια loop η οποια εχει ως συνθηκη η current να μην ξεπερασει τον κομβο copiedthis.head και ο currentlist να μην υπερβει τον κομβο copiedlist.head
			if (current.key() >= currentlist.key()){ //συγκρινω τα κελιδια των nodes μεταξυ τους 
				
				mergedlist.insertFirst(current.key()); //εισχωρω στην mergedlist το κλειδι του current αν ο current ειναι οντως μεγαλυτερος 
				current.prev.next=current.next;
				if (current.next != null){ //αλλαζουμε το pointer του επομενου απο τον προηγουμενο selected node να ειναι ο επόμενος node απο τον selected (selected = current)
					current.next.prev=current.prev;
				}
			
				current=current.prev; //επειτα παμε στον προηγουμενο κομβο της copiedthis
			}else{ //ιδια τεχνικη με πανω ακολουθουμε και οταν η συνθηκη του if ειναι αντιστροφη δηλαδη το currentlist.key να ειναι μεγαλυτερο του current.key 
				
				mergedlist.insertFirst(currentlist.key()); //εισχωρω το κλειδι στην mergedlist 
				currentlist.prev.next=currentlist.next; //και αλλαζω τα pointers καθως κανω remove των node απο την λιστα για να με διευκολυνει αυτο στο γεμισμα της mergedlist
				if (currentlist.next!=null){
					currentlist.next.prev=currentlist.prev;
				}
				
				currentlist=currentlist.prev; //επειτα ο κομβος currentlist παιρνει την τιμη του προηγουμενου κομβου
			}
		}
		// οι παρακατω loops γινονται αμα μια λιστα εχει αδειασει και δεν εχουν εισχωρηθει τα αλλα στοιχεια της αλλης λιστας στην mergedlist. Η λογικη πισω απο αυτο το κοοματι του κωδικα ειναι οτι εφοσον 
		// η πανω loop τηλειωσε και εφοσον και οι 2 λιστες ειναι sorted τοτε τα nodes που εχουν ξεμεινει ειναι σιγουρα μικροτερα του τελευταιου node που προστεθηκε στην mergedlist και απλα δεν προστεθηκαν
		// επειδη η μια απο τις 2 λιστες αδειασε, οποτε προστεθονται και τα τελευταια κλειδια των nodes απο απομεινανε στην mergedlist 
		while(current!=copiedthis.head){
			mergedlist.insertFirst(current.key()); 
				current.prev.next=current.next;
				if (current.next != null){ //αλλαζουμε το pointer του επομενου απο τον προηγουμενο selected node να ειναι ο επόμενος node απο τον selected (selected = current)
					current.next.prev=current.prev;
				}
				
				current=current.prev;
			
		}
		while(currentlist!=copiedlist.head){
			mergedlist.insertFirst(currentlist.key());
				currentlist.prev.next=currentlist.next; // παλι και εδω αλλαζουμε τα pointers
				if (currentlist.next!=null){
					currentlist.next.prev=currentlist.prev;
				}
				
				currentlist=currentlist.prev;
		}
		return mergedlist; //τελος γυρναμε την ολοκληρωμενη mergedlist 
	}

	public List largest(int k) { //η λογικη πισω απο την largest ειναι η δημιουργια μιας λιστας με ονομα largest η οποια θα περιεχει το Κ συνολο keys τα οποια ειναι τα μεγαλυτερα στην λιστα this 
		List largestcopy, largest = new List();
		largestcopy=this.copy(); //εφοσον δεν θελουμε να τροποποιηθει η λιστα this δημιουργουμε μια νεα λιστα η οποια θα ειναι αντιγραφο της this 
		int length=0; //αρχικοποιουμε μια μεταβλητη int την οποια θα την χρησιμοποιησουμε παρακατω 
		insertionSort(largestcopy); //χρησιμοποιουμε την insertionSort method για να ταξινομησουμε την λιστα 
		Node current = largestcopy.tail.prev; //με την ιδια λογικη που χρησιμοποιησα και στην mergedlist ετσι και εδω αρχικοποιω τον node current ως largestcopy.tail.prev
		Node lengthcounter=this.head.next; //ταυτοχρονα αρχικοποιουμε εναν αλλο node τον lengthcounter για να μετρησουμε το μεγεθος της λιστας this 
		while (lengthcounter!=this.tail){ //η μετρηση του length της λιστας this πραγματοποιειται με μια loop η οποια εχει ως συνθηκη το node lengthcounter να μην ξεπερασει τον node this.tail 
			length++; //αυξανουμε με καθε περασμα της λουπας την μεταβλητη length 
			lengthcounter=lengthcounter.next; //και επισης ο lengthcounter παιρνει την τιμη του επομενου κομβου 
		}
		if (length<k){ //σε αυτην την συνθηκη αν το μεγεθος του length ειναι μικροτερο απο αυτο της μεταβλητης k, τοτε η μεθοδος επιστρεφει μια κενη λιστα με ενα μηνυμα λαθους οτι το int k ειναι μεγαλυτερο απο το μεγεθος της λιστας 
			System.out.println("Invalid number, list doesnt have these much nodes ");
			return largest;
		}
		int count=1; // αμα το k ειναι μικροτερο απο το length τοτε η μεθοδος δεν εχει κανενα προβλημα και συνεχιζει κανονικα την εκτελεση της η οποια ξεκιναει με το να αχρικοποιησει μια μεταβλητη count ως 1
		while(count<=k){ //το count το χρησιμοποιουμε ετσι ωστε να μην ξεπερασουμε τον αριθμο k, δηλαδη το συνολο των μεγαλυτερων αριθμων της λιστας 
			largest.insertFirst(current.key()); //και προσθετουμε στην λιστα τον μεγαλυτερο αριθμο ο οποιος στην αρχη ειναι το tail.prev και με καθε περασμα αυτης της loop το αμεσως προηγουμενο node key προστιθεται στην λιστα largest 
			current=current.prev;
			count++;
		}
	return largest; //οταν τελειωσει η loop τοτε η μεθοδος επιστρεφει την largest list η οποια περιεχει το συνολο k των μεγαλυτερων αριθμων της λιστας this 
	}

	public static void insertionSort(List list) { //η λογικη πισω απο αυτην την μεθοδο ειναι η λογικη της insertion sort η οποια σε καθε περασμα ελεγχει καθε node με τα προηγουμενα για να δει αν ειναι στην σωστη θεση και αν δεν ειναι τοτε 
		                                      //μετακινει ολα τα προηγουμενα nodes ετσι ωστε να μεταφερθει στην σωστη θεση  
		Node currentInsertion = list.head.next; //αρχικοποιουμε λοιπον τον πρωτο node ο οποιος ειναι ο list.head.next
		while(currentInsertion!=list.tail){ //με αυτην την λουπα θα κανω οσες συγκρισεις οσα ειναι και τα nodes της λιστας για να ταξινομηθουν ολα 
			Node temp = currentInsertion; //η temp που θα χρειαστουμε αργοτερα για την ολισθηση των nodes παιρνει παντα την τιμη της currentInsertion, η οποια ειναι καθε node για τον οποιο ελεγχει η μεθοδος την προκειμενη στιγμη
			Node checking = currentInsertion.prev; //φτιαχνω αυτο το node για συγκρισεις με τα προηγουμενα node keys απο αυτον που συγκρινω
			while(checking!=list.head && currentInsertion!=null && temp.key() < checking.key()){  //συγκρινω αμα το παρων node ειναι μικροτερο απο το πορηγουμενο του 
				temp.prev.next=temp.next; //κανω τις απαραιτητες αλλαγες δεικτων για να ολισθησω την λιστα καθε φορα που ο κομβος που ελεγχεται αν ειναι μικροτερος απο τον προηγμουμενο 
				if (temp.next != null) {
					temp.next.prev = temp.prev;
				}
				temp.prev=checking.prev;
				checking.prev.next=temp;
				temp.next=checking;
				checking.prev=temp;
				checking=temp.prev;
				
			}
				currentInsertion=currentInsertion.next; //οταν γινει η ολισθηση και ολα τα προηγουμενα nodes ειναι sorted τοτε η currentInsertion παιρνει την επομενη τιμη μεχρι ωστε να φτασει το tail 
		}
	} //οταν η μεθοδος καλειται τοτε η λιστα που παιρνει ως ορισμα γινεται sorted με την μεθοδο της Insertion

	public static void main(String[] args) {			
}
