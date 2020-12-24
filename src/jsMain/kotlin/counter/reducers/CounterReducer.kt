package counter.reducers

import redux.RAction
import counter.actions.CountActions
import counter.actions.CountState


fun counterReducer(state: CountState = CountState(0, 0), action: RAction): CountState = when (action) {
    is CountActions.IncrementCount -> state.increment()
    is CountActions.DecrementCount -> state.decrement()
    else -> state
}
