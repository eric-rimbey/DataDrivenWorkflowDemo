package workflow;

import javax.swing.BoxLayout;

public enum Orientation {
	Horizontal {
		@Override
		public int asBoxLayoutAxis() {
			return BoxLayout.X_AXIS;
		}

		@Override
		public Orientation orthogonalOrientation() {
			return Orientation.Vertical;
		}
	},
	Vertical {
		@Override
		public int asBoxLayoutAxis() {
			return BoxLayout.Y_AXIS;
		}

		@Override
		public Orientation orthogonalOrientation() {
			return Orientation.Horizontal;
		}
	};

	public abstract int asBoxLayoutAxis();

	public abstract Orientation orthogonalOrientation();
}
