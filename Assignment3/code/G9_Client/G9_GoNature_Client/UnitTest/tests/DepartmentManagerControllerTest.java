package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import controllers.DepartmentManagerController;
import controllers.IRecievedFromServer;

class DepartmentManagerControllerTest {

	class stubRecievedFromServer implements IRecievedFromServer {

		@Override
		public void setRegularVisitorsToChart(ArrayList<Object> msgReceived) {
			ArrayList<Object> regular = new ArrayList<>();
			regular.add(13.0);
			regular.add(15.0);
			regular.add(10.0);
			regular.add(5.0);
			DepartmentManagerController.setRegularVisitors(regular);

		}

		@Override
		public void setMemberVisitorsToChart(ArrayList<Object> msgReceived) {
			ArrayList<Object> member = new ArrayList<>();
			member.add(13.0);
			member.add(15.0);
			member.add(10.0);
			member.add(5.0);
			DepartmentManagerController.setRegularVisitors(member);

		}

		@Override
		public void setGroupVisitorsToChart(ArrayList<Object> msgReceived) {
			ArrayList<Object> group = new ArrayList<>();
			group.add(13.0);
			group.add(15.0);
			group.add(10.0);
			group.add(5.0);
			DepartmentManagerController.setRegularVisitors(group);

		}
	}
	
	
	
	/******************************Test section start here******************************************/

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void test() {
		fail("Not yet implemented");
	}

}
